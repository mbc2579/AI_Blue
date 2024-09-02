package com.blue.service.domain.payment;

import com.blue.service.domain.BaseEntity;
import com.blue.service.domain.order.Order;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "p_payments")
public class Payment extends BaseEntity {
    @Id
    @Column(name = "payment_id")
    private UUID paymentId;

    @Column(name = "user_name", nullable = false , length = 100)
    private String userName;

    @OneToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @Column(name = "p_result_id")
    private String pResultId;

    @Column(name = "payment_amount")
    private Long paymentAmount;
}
