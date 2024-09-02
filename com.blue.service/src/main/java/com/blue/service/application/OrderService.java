package com.blue.service.application;

import com.blue.service.application.dtos.order.OrderCreateReqDto;
import com.blue.service.application.dtos.order.OrderResDto;
import com.blue.service.application.dtos.order.OrderUpdateReqDto;
import com.blue.service.domain.product.Product;
import com.blue.service.domain.product.ProductRepository;
import com.blue.service.domain.store.Store;
import com.blue.service.domain.store.StoreRepository;
import com.blue.service.domain.order.Order;
import com.blue.service.domain.order.OrderProduct;
import com.blue.service.domain.order.OrderProductRequest;
import com.blue.service.domain.order.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j(topic = "Order Service")
@Transactional(readOnly=true)
public class OrderService {

    private final OrderRepository orderRepository;
    private final StoreRepository storeRepository;
    private final ProductRepository productRepository;

    @Transactional
    public OrderResDto createOrder(OrderCreateReqDto orderCreateReqDto, String userName) {
        Store store = storeRepository.findByStoreIdAndDeletedAtIsNull(orderCreateReqDto.getStoreId()).orElseThrow(()->{
                    log.error("가게 정보를 찾을 수 없음");
                    return new ResponseStatusException(HttpStatus.NOT_FOUND, "가게 정보를 찾을 수 없음");
                }
        );
        Order order = orderCreateReqDto.toOrder(userName, store);

        for(OrderProductRequest productRequest : orderCreateReqDto.getOrderProductRequests()){
            Product product = productRepository.findByProductIdAndDeletedAtIsNull(productRequest.getProductId()).orElseThrow(()->{
                        log.error("상품 정보를 찾을 수 없음");
                        return new ResponseStatusException(HttpStatus.NOT_FOUND, "상품 정보를 찾을 수 없음");
                    }
            );

            if(productRequest.getProductQuantity() <= 0) {
                log.error("상품 수량이 잘못 되었음");
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "상품 수량이 잘못 되었음");
            }

            order.addOrderProduct(OrderProduct.builder()
                    .order(order)
                    .product(product)
                    .productQuantity(productRequest.getProductQuantity())
                    .build());
        }

        return OrderResDto.from(orderRepository.save(order));
    }

    public OrderResDto getOrder(UUID orderId, String userName) {
        Order order = orderRepository.findOneByOrderIdAndDeletedAtIsNull(orderId).orElseThrow(()->{
            log.error("주문 정보를 찾을 수 없음");
            return new ResponseStatusException(HttpStatus.NOT_FOUND, "주문 정보를 찾을 수 없음");
        }
        );
        if(!order.getUserName().equals(userName)){
            log.error("접근할 수 없음");
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "접근할 수 없음");
        }
        return OrderResDto.from(order);
    }

    public List<OrderResDto> searchOrders(String userName, int page, int limit,
                                          Boolean isAsc, String orderBy, String keyword) {
        Sort.Direction direction;
        if(isAsc){
            direction = Sort.Direction.ASC;
        }else {
            direction = Sort.Direction.DESC;
        }
        Pageable pageable = PageRequest.of(page-1, limit, Sort.by(direction, orderBy));

        Page<OrderResDto> orderPage = orderRepository.searchByUserName(userName, keyword, pageable).map(OrderResDto::from);

        return orderPage.toList();
    }

    @Transactional
    public OrderResDto updateOrder(OrderUpdateReqDto orderUpdateReqDto, String userName){
        // 본인의 주문인지 확인
        Order order = orderRepository.findOneByOrderIdAndDeletedAtIsNull(orderUpdateReqDto.getOrderId()).orElseThrow(()->{
                    log.error("주문 정보를 찾을 수 없음");
                    return new ResponseStatusException(HttpStatus.NOT_FOUND, "주문 정보를 찾을 수 없음");
                }
        );
        if(!order.getUserName().equals(userName)){
            log.error("접근할 수 없음");
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "접근할 수 없음");
        }
        // 주문 정보 수정 ( 상품 리스트 수정 )
        List<OrderProduct> updatedOrderProducts = new ArrayList<>();
        for(OrderProductRequest productRequest : orderUpdateReqDto.getOrderProductRequests()){
            Product product = productRepository.findByProductIdAndDeletedAtIsNull(productRequest.getProductId()).orElseThrow(()->{
                        log.error("상품 정보를 찾을 수 없음");
                        return new ResponseStatusException(HttpStatus.NOT_FOUND, "상품 정보를 찾을 수 없음");
                    }
            );

            if(productRequest.getProductQuantity() <= 0) {
                log.error("상품 수량이 잘못 되었음");
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "상품 수량이 잘못 되었음");
            }

            updatedOrderProducts.add(OrderProduct.builder()
                    .order(order)
                    .product(product)
                    .productQuantity(productRequest.getProductQuantity())
                    .build());
        }
        order.updateOrderProducts(updatedOrderProducts);

        return OrderResDto.from(order);
    }

    @Transactional
    public void deleteOrder(UUID orderId, String userName){
        // 본인의 주문인지 확인
        Order order = orderRepository.findOneByOrderIdAndDeletedAtIsNull(orderId).orElseThrow(()->{
                    log.error("주문 정보를 찾을 수 없음");
                    return new ResponseStatusException(HttpStatus.NOT_FOUND, "주문 정보를 찾을 수 없음");
                }
        );
        if(!order.getUserName().equals(userName)){
            log.error("접근할 수 없음");
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "접근할 수 없음");
        }
        // 주문 삭제 ( 5분이 넘어가면 주문 삭제 불가 )
        LocalDateTime now = LocalDateTime.now();
        LocalTime currentTime = now.toLocalTime();
        LocalTime createdTime = order.getCreatedAt().toLocalTime();
        long diffMin = Duration.between(createdTime, currentTime).isNegative() ?
                Duration.between(createdTime, now).plusDays(1).toMinutes() : Duration.between(createdTime, currentTime).toMinutes();
        if(diffMin >= 5 || !now.toLocalDate().equals(order.getCreatedAt().toLocalDate())){
            log.error("주문 후 5분 초과로 취소 불가");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "주문 후 5분 초과로 취소 불가");
        }

        // OrderProducts Soft Delete
        order.deleteOrderProduct(userName);
        // Order Soft Delete
        order.setDeleted(LocalDateTime.now(), userName);
    }

}
