package com.blue.service.domain;

import com.blue.service.application.dtos.StoreReqDto;
import jakarta.persistence.*;
import lombok.*;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder(access = AccessLevel.PRIVATE)
@Table(name = "p_stores")
public class Store extends BaseEntity{
    @Id
    @GeneratedValue
    private UUID storeId = UUID.randomUUID();

    private String userName;
    private String storeName;
    private String storeDescription;
    private Integer regionId;
    private Integer categoryId;


    public static Store createStore(StoreReqDto requestDto, String userName) {
        return Store.builder()
                .storeName(requestDto.getStoreName())
                .storeDescription(requestDto.getStoreDescription())
                .regionId(requestDto.getRegionId())
                .categoryId(requestDto.getCategoryId())
                .userName(userName)
                .build();
    }

    public void update(StoreReqDto requestDto) {
        this.storeName = requestDto.getStoreName();
        this.storeDescription = requestDto.getStoreDescription();
        this.regionId = requestDto.getRegionId();
        this.categoryId = requestDto.getCategoryId();
    }
}
