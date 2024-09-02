package com.blue.service.controller;

import com.blue.service.application.PaymentService;
import com.blue.service.application.dtos.payment.PaymentCreateReqDto;
import com.blue.service.application.dtos.payment.PaymentResDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/payments")
@Slf4j(topic = "PaymentController")
@Tag(name = "Payment", description = "Payment API")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping
    @Operation(summary = "결제 정보 생성")
    public ResponseEntity<PaymentResDto> createPayment(
            @RequestBody PaymentCreateReqDto paymentCreateReqDto,
            @Parameter(description = "Gateway에서 넘겨받는 UserName Header")
            @RequestHeader(name = "X-User-Name", required = false)  String userName
    ) {
        return ResponseEntity.ok(paymentService.createPayment(paymentCreateReqDto, userName));
    }

    @GetMapping("/{paymentId}")
    @Operation(summary = "결제 정보 단건 조회")
    public ResponseEntity<PaymentResDto> getPayment(
            @PathVariable(name = "paymentId") UUID paymentId,
            @Parameter(description = "Gateway에서 넘겨받는 UserName Header")
            @RequestHeader(name = "X-User-Name", required = false) String userName
    ){
        return ResponseEntity.ok(paymentService.getPayment(paymentId, userName));
    }

    @GetMapping
    @Operation(summary = "결제 정보 검색")
    public ResponseEntity<List<PaymentResDto>> searchPayment(
            @Parameter(description = "Gateway에서 넘겨받는 UserName Header")
            @RequestHeader(name = "X-User-Name", required = false) String userName,
            @RequestParam(name = "page") int page,
            @RequestParam(name = "limit") int limit,
            @RequestParam(name = "isAsc") Boolean isAsc,
            @RequestParam(name = "paymentBy") String paymentBy
    ){
        return ResponseEntity.ok(paymentService.searchPayments(userName, page, limit, isAsc, paymentBy));
    }

    @DeleteMapping("/{paymentId}")
    @Operation(summary = "결제 정보 삭제")
    public ResponseEntity<String> deletePayment(
            @PathVariable(name = "paymentId")UUID paymentId,
            @Parameter(description = "Gateway에서 넘겨받는 UserName Header")
            @RequestHeader(name = "X-User-Name", required = false) String userName
    ){
        paymentService.deletePayment(paymentId, userName);
        return ResponseEntity.ok("결제 정보 삭제 성공");
    }
}
