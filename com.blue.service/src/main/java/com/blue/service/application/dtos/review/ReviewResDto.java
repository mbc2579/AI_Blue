package com.blue.service.application.dtos.review;

import com.blue.service.domain.review.Review;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ReviewResDto {
    private UUID reviewId;
    private String comment;
    private Integer star;

    public ReviewResDto(Review review) {
        this.reviewId = review.getReviewId();
        this.comment = review.getComment();
        this.star = review.getStar();
    }
}
