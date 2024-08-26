package com.blue.service.domain.order;

import com.blue.service.domain.BaseEntity;
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
@Entity(name = "p_orders")
public class Order extends BaseEntity {
    @Id
    @Column(name = "order_id")
    private UUID orderId;

    @Column(name = "user_name", nullable = false , length = 100)
    private String userName;

//    @ManyToOne
//    @JoinColumn(name = "store_id")
    @Column(name = "store_id", nullable = false)
    private UUID storeId;

//    @ManyToOne
//    @JoinColumn(name = "dest_id")
    @Column(name = "dest_id", nullable = false)
    private UUID destId;

    @Column(name = "order_type")
    private OrderType orderType;

    @Column(name = "is_reviewed")
    private boolean isReviewed;

}
