package com.blue.service.application;

import com.blue.service.application.dtos.order.OrderCreateReqDto;
import com.blue.service.application.dtos.order.OrderResDto;
import com.blue.service.application.dtos.order.OrderUpdateReqDto;
import com.blue.service.domain.order.Order;
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

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j(topic = "Order Service")
@Transactional(readOnly=true)
public class OrderService {

    private final OrderRepository orderRepository;

    @Transactional
    public OrderResDto createOrder(OrderCreateReqDto orderCreateReqDto, String userName) {
        Order order = orderCreateReqDto.toOrder(userName);
        // Product 부분 개발이 완료되면 상품 주문 로직 추가 예정
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
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "접근할 수 없음");
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
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "접근할 수 없음");
        }
        // 주문 정보 수정 ( 현재는 review 상태 변경 )
        order.updateOrder(orderUpdateReqDto.getIsReviewed());
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
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "접근할 수 없음");
        }
        // 주문 삭제
        order.setDeleted(LocalDateTime.now(), userName);
    }

}
