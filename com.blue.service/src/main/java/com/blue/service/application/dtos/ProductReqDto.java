package com.blue.service.application.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ProductReqDto {
    private String description;
    private String productName;
    private Integer productPrice;
    private Boolean isVisible;

}
