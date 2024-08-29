package com.blue.service.application.dtos;

import com.blue.service.domain.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductResDto {
    private UUID productId;
    private String productName;
    private String productPrice;

    public ProductResDto(Product product) {
        this.productId = product.getProductId();
        this.productName = product.getProductName();
        this.productPrice = product.getProductPrice().toString();
    }
}
