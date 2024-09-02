package com.blue.service.application;

import com.blue.service.application.dtos.store.StoreReqDto;
import com.blue.service.application.dtos.store.StoreResDto;
import com.blue.service.domain.store.Store;
import com.blue.service.domain.store.StoreRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StoreService {

    private final StoreRepository storeRepository;

    
    // 가게 추가
    public StoreResDto createStore(StoreReqDto requestDto, String userName) {
        Store store = Store.createStore(requestDto, userName);
        Store savedStore = storeRepository.save(store);
        return toResponseDto(savedStore);
    }

    // 가게 검색
    public List<StoreResDto> searchStore(String userName, int page, int limit, Boolean isAsc, String orderBy, String keyword, String storeName) {
        Sort.Direction direction;

        if(isAsc) {
            direction = Sort.Direction.ASC;
        } else {
            direction = Sort.Direction.DESC;
        }

        Pageable pageable = PageRequest.of(page-1, limit, Sort.by(direction, orderBy));

        Page<StoreResDto> storePage = storeRepository.searchByStoreName(userName, storeName, keyword, pageable).map(StoreResDto::from);

        return storePage.toList();
    }

    // 가게 상세 조회
    public StoreResDto lookupStore(UUID storeId) {
        Optional<Store> storeOpt = storeRepository.findByStoreIdAndDeletedAtIsNull(storeId);
        if (storeOpt.isEmpty()) {
            throw new EntityNotFoundException("가게를 찾을 수 없거나 삭제되었습니다.");
        }
        return new StoreResDto(storeOpt.get());
    }

    
    // 가게 수정
    @Transactional
    public ResponseEntity<StoreResDto> updateStore(UUID storeId, StoreReqDto requestDto, String userName) {
        Optional<Store> store = storeRepository.findById(storeId);
        if(!store.isPresent() || !store.get().getUserName().equals(userName)) {
            return ResponseEntity.status(400).body(toResponseDto(store.get()));
        }

        store.get().update(requestDto);

        return ResponseEntity.status(200).body(new StoreResDto(store.get()));
    }

    // 가게 삭제
    @Transactional
    public ResponseEntity<StoreResDto> deleteStore(UUID storeId, String userName) {
        Optional<Store> storeOpt = storeRepository.findById(storeId);

        if(!storeOpt.isPresent() || !storeOpt.get().getUserName().equals(userName)) {
            return ResponseEntity.status(400).body(toResponseDto(storeOpt.get()));
        }

        Store store = storeOpt.get();

        store.setDeleted(LocalDateTime.now(), userName);
        storeRepository.save(store);

        return ResponseEntity.status(200).body(new StoreResDto(store));
    }

    private StoreResDto toResponseDto(Store store) {
        return new StoreResDto(
                store.getStoreId(),
                store.getStoreName(),
                store.getStoreDescription(),
                store.getRegionId(),
                store.getCategoryId()
        );
    }
}
