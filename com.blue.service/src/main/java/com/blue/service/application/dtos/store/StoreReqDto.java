package com.blue.service.application.dtos.store;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class StoreReqDto {
    private String storeName;
    private String storeDescription;
    private Integer regionId;
    private Integer categoryId;
}
