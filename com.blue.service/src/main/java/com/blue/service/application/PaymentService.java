package com.blue.service.application;

import com.blue.service.application.dtos.payment.PaymentCreateReqDto;
import com.blue.service.application.dtos.payment.PaymentResDto;
import com.blue.service.domain.order.Order;
import com.blue.service.domain.order.OrderRepository;
import com.blue.service.domain.payment.Payment;
import com.blue.service.domain.payment.PaymentRepository;
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
@Slf4j(topic = "PaymentService")
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;

    @Transactional
    public PaymentResDto createPayment(PaymentCreateReqDto createReqDto, String userName){
        Order order = orderRepository.findOneByOrderIdAndDeletedAtIsNull(createReqDto.getOrderId()).orElseThrow(()->{
                    log.error("주문 정보를 찾을 수 없음");
                    return new ResponseStatusException(HttpStatus.NOT_FOUND, "주문 정보를 찾을 수 없음");
        });
        return PaymentResDto.from(paymentRepository.save(createReqDto.toPayment(userName, order)));
    }

    public PaymentResDto getPayment(UUID paymentId, String userName){
        Payment payment = paymentRepository.findOneByPaymentIdAndDeletedAtIsNull(paymentId).orElseThrow(()->{
            log.error("결제 정보를 찾을 수 없음");
            return new ResponseStatusException(HttpStatus.NOT_FOUND, "주문 정보를 찾을 수 없음");
        });
        if(!payment.getUserName().equals(userName)){
            log.error("접근할 수 없음");
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "접근할 수 없음");
        }
        return PaymentResDto.from(payment);
    }

    public List<PaymentResDto> searchPayments(String userName, int page, int limit,
                                              Boolean isAsc, String orderBy){
        Sort.Direction direction;
        if(isAsc){
            direction = Sort.Direction.ASC;
        }else {
            direction = Sort.Direction.DESC;
        }
        Pageable pageable = PageRequest.of(page-1, limit, Sort.by(direction, orderBy));

        Page<PaymentResDto> paymentPage = paymentRepository.searchByUserName(userName, pageable).map(PaymentResDto::from);

        return paymentPage.toList();
    }

    @Transactional
    public void deletePayment(UUID paymentId, String userName){
        Payment payment = paymentRepository.findOneByPaymentIdAndDeletedAtIsNull(paymentId).orElseThrow(()->{
            log.error("결제 정보를 찾을 수 없음");
            return new ResponseStatusException(HttpStatus.NOT_FOUND, "주문 정보를 찾을 수 없음");
        });
        if(!payment.getUserName().equals(userName)){
            log.error("접근할 수 없음");
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "접근할 수 없음");
        }
        //결제 정보 삭제
        payment.setDeleted(LocalDateTime.now(), userName);
    }
}
