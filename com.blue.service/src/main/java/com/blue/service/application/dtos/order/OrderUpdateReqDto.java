package com.blue.service.application.dtos.order;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderUpdateReqDto {
    private UUID orderId;
    private Boolean isReviewed;
}
