package com.blue.service.controller;

import com.blue.service.application.ReviewService;
import com.blue.service.application.dtos.review.ReviewReqDto;
import com.blue.service.application.dtos.review.ReviewResDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequestMapping("/store")
@RestController
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    // 리뷰 작성
    @PostMapping("/{orderId}/review/{storeId}")
    @ResponseBody
    public ReviewResDto createReview(
            @Valid
            @PathVariable UUID orderId,
            @PathVariable UUID storeId,
            @RequestBody ReviewReqDto reqDto,
            @RequestHeader(name = "X-User-Name", required = false) String userName) {
        return reviewService.createReview(orderId,storeId, reqDto, userName);
    }

    // 해당가게 리뷰 전체 조회
    @GetMapping("/{storeId}/review")
    @ResponseBody
    public List<ReviewResDto> getReview(@PathVariable UUID storeId) {
        return reviewService.getReview(storeId);
    }

    // 리뷰 수정
    @PutMapping("/review/{reviewId}")
    @ResponseBody
    public ReviewResDto updateReview(
            @PathVariable UUID reviewId,
            @RequestBody ReviewReqDto reqDto,
            @RequestHeader(name = "X-User-Name", required = false) String userName) {
        return reviewService.updateReview(reviewId, reqDto, userName);
    }

    // 리뷰 삭제
    @DeleteMapping("/{storeId}/review/{reviewId}")
    @ResponseBody
    public ResponseEntity<ReviewResDto> deleteReview(
            @PathVariable UUID storeId,
            @PathVariable UUID reviewId,
            @RequestHeader(name = "X-User-Name", required = false) String userName) {
        return reviewService.deleteReview(storeId, reviewId, userName);
    }
}
