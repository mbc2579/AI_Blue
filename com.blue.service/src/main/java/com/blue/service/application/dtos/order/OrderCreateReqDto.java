package com.blue.service.application.dtos.order;

import com.blue.service.domain.order.Order;
import com.blue.service.domain.order.OrderType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderCreateReqDto {
    private UUID storeId;
    private UUID destId;
    private OrderType orderType;

    public Order toOrder(String userName) {
        return Order.builder()
                .orderId(UUID.randomUUID())
                .storeId(this.storeId)
                .userName(userName)
                .destId(this.destId)
                .orderType(this.orderType)
                .isReviewed(false)
                .build();
    }
}
