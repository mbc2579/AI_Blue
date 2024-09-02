package com.blue.service.domain.order;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderProductRequest {
    private UUID productId;
    private Long productQuantity;

    public static OrderProductRequest from(OrderProduct orderProduct) {
        return OrderProductRequest.builder()
                .productId(orderProduct.getProduct().getProductId())
                .productQuantity(orderProduct.getProductQuantity())
                .build();
    }

    public static List<OrderProductRequest> from(List<OrderProduct> orderProducts) {
        List<OrderProductRequest> orderProductRequests = new ArrayList<>();
        for (OrderProduct orderProduct : orderProducts) {
            orderProductRequests.add(OrderProductRequest.from(orderProduct));
        }
        return orderProductRequests;
    }
}
