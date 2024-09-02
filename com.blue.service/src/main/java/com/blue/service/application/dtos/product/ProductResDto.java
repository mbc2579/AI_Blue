package com.blue.service.application.dtos.product;

import com.blue.service.domain.product.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductResDto {
    private UUID productId;
    private String productName;
    private String productPrice;
    private String description;

    public ProductResDto(Product product) {
        this.productId = product.getProductId();
        this.productName = product.getProductName();
        this.productPrice = product.getProductPrice().toString();
        this.description = product.getDescription();
    }

    public static ProductResDto from(Product product) {
        return ProductResDto.builder()
                .productId(product.getProductId())
                .productName(product.getProductName())
                .productPrice(String.valueOf(product.getProductPrice()))
                .description(product.getDescription())
                .build();
    }
}
