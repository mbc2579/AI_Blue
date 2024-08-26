package com.blue.service.domain.payment;

import com.blue.service.domain.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
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

    //    @OneToOne
//    @JoinColumn(name = "order_id")
    @Column(name = "order_id", nullable = false)
    private UUID orderId;

    @Column(name = "p_result_id")
    private String pResultId;

    @Column(name = "payment_amount")
    private Long paymentAmount;
}
