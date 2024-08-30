package com.blue.service.domain.order;

import com.blue.service.domain.BaseEntity;
import com.blue.service.domain.Store;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
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

    @ManyToOne
    @JoinColumn(name = "store_id")
    private Store store;

    @OneToMany(mappedBy = "order", cascade = CascadeType.MERGE)
    private List<OrderProduct> orderProducts;

//    @ManyToOne
//    @JoinColumn(name = "dest_id")
    @Column(name = "dest_id", nullable = false)
    private UUID destId;

    @Column(name = "order_type")
    private OrderType orderType;

    @Column(name = "is_reviewed")
    private boolean isReviewed;

    public void addOrderProduct(OrderProduct orderProduct) {
        this.orderProducts.add(orderProduct);
    }

    public void deleteOrderProduct(String userName) {
        for(OrderProduct orderProduct : this.orderProducts) {
            orderProduct.setDeleted(LocalDateTime.now(), userName);
        }
    }

    public void updateOrder(boolean isReviewed){
        this.isReviewed = isReviewed;
    }
}
