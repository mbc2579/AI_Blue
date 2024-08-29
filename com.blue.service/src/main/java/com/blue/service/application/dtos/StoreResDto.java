package com.blue.service.application.dtos;

import com.blue.service.domain.Store;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class StoreResDto {
    private UUID storeId;
    private String storeName;
    private String storeDescription;
    private Integer regionId;
    private Integer categoryId;

    public StoreResDto(Store store) {
        this.storeId = store.getStoreId();
        this.storeName = store.getStoreName();
        this.storeDescription = store.getStoreDescription();
        this.regionId = store.getRegionId();
        this.categoryId = store.getCategoryId();
    }
}
