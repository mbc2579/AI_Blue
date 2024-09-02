package com.blue.service.application.dtos.order;

import com.blue.service.domain.store.Store;
import com.blue.service.domain.order.Order;
import com.blue.service.domain.order.OrderProductRequest;
import com.blue.service.domain.order.OrderType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderCreateReqDto {
    private UUID storeId;
    private UUID destId;
    private List<OrderProductRequest> orderProductRequests;
    private OrderType orderType;

    public Order toOrder(String userName, Store store) {
        return Order.builder()
                .orderId(UUID.randomUUID())
                .store(store)
                .orderProducts(new ArrayList<>())
                .userName(userName)
                .destId(this.destId)
                .orderType(this.orderType)
//                .isReviewed(false)
                .build();
    }
}
