package com.blue.service.domain;

import com.blue.service.application.dtos.ProductReqDto;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder(access = AccessLevel.PRIVATE)
@Table(name = "p_products")
public class Product extends BaseEntity{
    @Id
    @GeneratedValue
    private UUID productId = UUID.randomUUID();

    @ManyToOne
    @JoinColumn(name = "storeId", nullable = false)
    private Store store;

    private String productName;
    private Integer productPrice;
    private String description;
    private Boolean isVisible;

    public Product(ProductReqDto requestDto, Store store, String description){
        this.productName = requestDto.getProductName();
        this.productPrice = requestDto.getProductPrice();
        this.isVisible = requestDto.getIsVisible();
        this.description = description;
        this.store = store;
    }

    public void update(ProductReqDto requestDto){
        this.productName = requestDto.getProductName();
        this.productPrice = requestDto.getProductPrice();
        this.isVisible = requestDto.getIsVisible();
    }

    // 설명 업데이트 메서드
    public void setDescription(String description) {
        this.description = description;
    }
}
