package com.blue.service.controller;

import com.blue.service.application.OrderService;
import com.blue.service.application.dtos.order.OrderCreateReqDto;
import com.blue.service.application.dtos.order.OrderResDto;
import com.blue.service.application.dtos.order.OrderUpdateReqDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@Tag(name = "Order", description = "Order API")
@Slf4j(topic = "Order Controller")
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    @Operation(summary = "주문 생성")
    public ResponseEntity<OrderResDto> createOrder(
            @RequestBody OrderCreateReqDto orderCreateReqDto,
            @RequestHeader(name = "X-User-Name") String userName
                                                        ) {
        return ResponseEntity.ok(orderService.createOrder(orderCreateReqDto, userName));
    }

    @GetMapping("/{orderId}")
    @Operation(summary = "주문 단건 조회")
    public ResponseEntity<OrderResDto> getOrder(
            @PathVariable(name = "orderId")UUID orderId,
            @RequestHeader(name = "X-User-Name") String userName
            ){
        return ResponseEntity.ok(orderService.getOrder(orderId, userName));
    }

    @PutMapping
    @Operation(summary = "주문 수정")
    public ResponseEntity<OrderResDto> updateOrder(
            @RequestBody OrderUpdateReqDto orderUpdateReqDto,
            @RequestHeader(name = "X-User-Name") String userName
    ){
        return ResponseEntity.ok(orderService.updateOrder(orderUpdateReqDto, userName));
    }

    @DeleteMapping("/{orderId}")
    @Operation(summary = "주문 삭제")
    public ResponseEntity<String> deleteOrder(
            @PathVariable(name = "orderId")UUID orderId,
            @RequestHeader(name = "X-User-Name") String userName
    ){
        orderService.deleteOrder(orderId, userName);
        return ResponseEntity.ok("삭제 성공");
    }
}
