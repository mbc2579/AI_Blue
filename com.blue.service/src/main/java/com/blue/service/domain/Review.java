package com.blue.service.domain;

import com.blue.service.application.dtos.ReviewReqDto;
import com.blue.service.domain.order.Order;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "p_reviews")
public class Review extends BaseEntity{
    @Id
    @GeneratedValue
    private UUID reviewId = UUID.randomUUID();

    private String username;

    @ManyToOne
    @JoinColumn(name = "orderId", nullable = false)
    private Order order;

    @ManyToOne
    @JoinColumn(name = "storeId", nullable = false)
    private Store store;

    private String comment;
    @Min(1)
    @Max(5)
    private Integer star;


    public Review(ReviewReqDto reqDto, Order order, Store store, String username) {
        this.comment = reqDto.getComment();
        this.star = reqDto.getStar();
        this.username = username;
        this.order = order;
        this.store = store;
    }
}
