package com.blue.service.application.dtos.payment;

import com.blue.service.domain.order.Order;
import com.blue.service.domain.payment.Payment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentCreateReqDto {
    private UUID orderId;
    private String pResultId;
    private Long paymentAmount;

    public Payment toPayment(String userName, Order order) {
        return Payment.builder()
                .userName(userName)
                .order(order)
                .pResultId(this.pResultId)
                .paymentAmount(this.paymentAmount)
                .build();
    }
}
