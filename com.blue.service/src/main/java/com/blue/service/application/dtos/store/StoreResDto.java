package com.blue.service.application.dtos.store;

import com.blue.service.domain.store.Store;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@Builder
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

    public static StoreResDto from(Store store) {
        return StoreResDto.builder()
                .storeId(store.getStoreId())
                .storeName(store.getStoreName())
                .storeDescription(store.getStoreDescription())
                .regionId(store.getRegionId())
                .categoryId(store.getCategoryId())
                .build();
    }
}
