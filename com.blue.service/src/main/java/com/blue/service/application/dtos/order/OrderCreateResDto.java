package com.blue.service.application.dtos.order;

import com.blue.service.domain.order.Order;
import com.blue.service.domain.order.OrderType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderCreateResDto {
    private UUID orderId;
    private String userName;
    private UUID storeId;
    private UUID destId;
    private OrderType orderType;
    private boolean isReviewed;

    public static OrderCreateResDto from(Order order) {
        return OrderCreateResDto.builder()
                .orderId(order.getOrderId())
                .userName(order.getUserName())
                .storeId(order.getStoreId())
                .destId(order.getDestId())
                .orderType(order.getOrderType())
                .isReviewed(order.isReviewed())
                .build();
    }
}
