package com.blue.service.application.dtos.order;

import com.blue.service.domain.order.Order;
import com.blue.service.domain.order.OrderProductRequest;
import com.blue.service.domain.order.OrderType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderResDto {
    private UUID orderId;
    private String userName;
    private UUID storeId;
    private List<OrderProductRequest> orderProducts;
    private UUID destId;
    private OrderType orderType;
    private boolean isReviewed;

    public static OrderResDto from(Order order) {
        return OrderResDto.builder()
                .orderId(order.getOrderId())
                .userName(order.getUserName())
                .storeId(order.getStore().getStoreId())
                .orderProducts(OrderProductRequest.from(order.getOrderProducts()))
                .destId(order.getDestId())
                .orderType(order.getOrderType())
                .isReviewed(order.isReviewed())
                .build();
    }
}
