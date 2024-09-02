package com.blue.service.controller;

import com.blue.service.application.OrderService;
import com.blue.service.application.dtos.order.OrderCreateReqDto;
import com.blue.service.application.dtos.order.OrderResDto;
import com.blue.service.application.dtos.order.OrderUpdateReqDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
            @Parameter(description = "Gateway에서 넘겨받는 UserName Header")
            @RequestHeader(name = "X-User-Name", required = false) String userName
                                                        ) {
        return ResponseEntity.ok(orderService.createOrder(orderCreateReqDto, userName));
    }

    @GetMapping("/{orderId}")
    @Operation(summary = "주문 단건 조회")
    public ResponseEntity<OrderResDto> getOrder(
            @PathVariable(name = "orderId")UUID orderId,
            @Parameter(description = "Gateway에서 넘겨받는 UserName Header")
            @RequestHeader(name = "X-User-Name", required = false) String userName
            ){
        return ResponseEntity.ok(orderService.getOrder(orderId, userName));
    }

    @GetMapping
    @Operation(summary = "주문 검색")
    public ResponseEntity<List<OrderResDto>> searchOrder(
            @Parameter(description = "Gateway에서 넘겨받는 UserName Header")
            @RequestHeader(name = "X-User-Name", required = false) String userName,
            @RequestParam(name = "page") int page,
            @RequestParam(name = "limit") int limit,
            @RequestParam(name = "isAsc") Boolean isAsc,
            @RequestParam(name = "orderBy") String orderBy,
            @RequestParam(name = "keyword", required = false) String keyword
    ){
        return ResponseEntity.ok(orderService.searchOrders(userName, page, limit, isAsc, orderBy, keyword));
    }

    @PutMapping
    @Operation(summary = "주문 수정")
    public ResponseEntity<OrderResDto> updateOrder(
            @RequestBody OrderUpdateReqDto orderUpdateReqDto,
            @Parameter(description = "Gateway에서 넘겨받는 UserName Header")
            @RequestHeader(name = "X-User-Name", required = false) String userName
    ){
        return ResponseEntity.ok(orderService.updateOrder(orderUpdateReqDto, userName));
    }

    @DeleteMapping("/{orderId}")
    @Operation(summary = "주문 삭제")
    public ResponseEntity<String> deleteOrder(
            @PathVariable(name = "orderId")UUID orderId,
            @Parameter(description = "Gateway에서 넘겨받는 UserName Header")
            @RequestHeader(name = "X-User-Name", required = false) String userName
    ){
        orderService.deleteOrder(orderId, userName);
        return ResponseEntity.ok("주문 삭제 성공");
    }
}
