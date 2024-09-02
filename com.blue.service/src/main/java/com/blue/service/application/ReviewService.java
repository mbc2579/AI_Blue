package com.blue.service.application;

import com.blue.service.application.dtos.ReviewReqDto;
import com.blue.service.application.dtos.ReviewResDto;
import com.blue.service.domain.Review;
import com.blue.service.domain.ReviewRepository;
import com.blue.service.domain.Store;
import com.blue.service.domain.StoreRepository;
import com.blue.service.domain.order.Order;
import com.blue.service.domain.order.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReviewService {

    private final OrderRepository orderRepository;
    private final ReviewRepository reviewRepository;
    private final StoreRepository storeRepository;

    // 리뷰 작성
    public ReviewResDto createReview(UUID orderId, UUID storeId, ReviewReqDto reqDto, String userName) {
        Optional<Order> order = orderRepository.findById(orderId);
        Optional<Store> store = storeRepository.findById(storeId);

        if(!order.isPresent() || !store.isPresent() || !order.get().getUserName().equals(userName)) {
            log.error("주문이 존재하지 않거나 주문자만 작성 가능합니다.");
            return null;
        }

        Review review = new Review(reqDto, order.get(), store.get(), userName);
        reviewRepository.save(review);
        return new ReviewResDto(review);
    }

    // 해당가게 리뷰 전체 조회
    public List<ReviewResDto> getReview(UUID storeId) {
        List<Review> reviewList = reviewRepository.findAllByStore_StoreIdAndDeletedAtIsNullOrderByCreatedAt(storeId);
        return reviewList.stream().map(ReviewResDto::new).collect(Collectors.toList());
    }


    // 리뷰 수정
    @Transactional
    public ReviewResDto updateReview(UUID reviewId, ReviewReqDto reqDto, String userName) {
        Optional<Review> checkReview = reviewRepository.findById(reviewId);
        if(!checkReview.isPresent()) {
            log.error("해당 리뷰가 존재하지 않습니다.");
            return null;
        }
        Review review = checkReview.get();
        String reviewUser = review.getOrder().getUserName();

        if(!reviewUser.equals(userName)) {
            log.error("주문자만 수정이 가능합니다.");
            return null;
        }

        reviewRepository.save(review);
        return new ReviewResDto(review);
    }

    // 리뷰 삭제
    @Transactional
    public ResponseEntity<ReviewResDto> deleteReview(UUID storeId, UUID reviewId, String userName) {
        Optional<Store> store = storeRepository.findById(storeId);
        Optional<Review> review = reviewRepository.findById(reviewId);

        if(!store.isPresent() || !review.isPresent()) {
            log.error("가게 또는 리뷰가 존재하지 않습니다.");
            return null;
        }

        if(!review.get().getUsername().equals(userName)) {
            log.error("리뷰 작성자만 삭제 가능합니다.");
            return null;
        }

        Review reviews = review.get();

        reviews.setDeleted(LocalDateTime.now(), userName);
        reviewRepository.save(reviews);

        return ResponseEntity.status(200).body(new ReviewResDto(reviews));
    }
}
