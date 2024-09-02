package com.blue.service.application.dtos.order;

import com.blue.service.domain.order.OrderProductRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderUpdateReqDto {
    private UUID orderId;
    private List<OrderProductRequest> orderProductRequests;
//    private Boolean isReviewed;
}
