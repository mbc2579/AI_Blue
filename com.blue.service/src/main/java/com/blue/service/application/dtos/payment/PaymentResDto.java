package com.blue.service.application.dtos.payment;

import com.blue.service.domain.payment.Payment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentResDto {
    private UUID paymentId;
    private String userName;
    private UUID orderId;
    private String pResultId;
    private Long paymentAmount;

    public static PaymentResDto from(Payment payment) {
        return PaymentResDto.builder()
                .paymentId(payment.getPaymentId())
                .userName(payment.getUserName())
                .orderId(payment.getOrder().getOrderId())
                .pResultId(payment.getPResultId())
                .paymentAmount(payment.getPaymentAmount())
                .build();
    }
}
