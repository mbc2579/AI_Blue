package com.blue.service.controller;

import com.blue.service.application.dtos.StoreReqDto;
import com.blue.service.application.dtos.StoreResDto;
import com.blue.service.application.StoreService;
import com.blue.service.domain.Store;
import com.blue.service.domain.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/stores")
public class StoreController {

    private final StoreService storeService;
    private final StoreRepository storeRepository;


    // 가게 추가
    @PostMapping
    public ResponseEntity<StoreResDto> createStore(@RequestBody StoreReqDto requestDto, @RequestHeader(name = "X-User-Name") String userName) {
        StoreResDto store = storeService.createStore(requestDto, userName);
        return ResponseEntity.ok(store);
    }

    // 가게 전체 조회
    @GetMapping
    @ResponseBody
    public List<StoreResDto> getStore() {
        List<Store> stores = storeRepository.findAllByDeletedAtIsNull();
        return stores.stream().map(StoreResDto::new).collect(Collectors.toList());
    }

    // 가게 상세 조회
    @GetMapping("/{storeId}")
    public ResponseEntity<StoreResDto> lookupStore(@PathVariable UUID storeId) {
        StoreResDto store = storeService.lookupStore(storeId);
        return ResponseEntity.ok(store);
    }

    // 가게 수정
    @PutMapping("/{storeId}")
    public ResponseEntity<StoreResDto> updateStore(@PathVariable UUID storeId, @RequestBody StoreReqDto requestDto, @RequestHeader(name = "X-User-Name") String userName) {
        return storeService.updateStore(storeId, requestDto, userName);
    }

    // 가게 삭제
    @DeleteMapping("/{storeId}")
    @ResponseBody
    public ResponseEntity<StoreResDto> deleteStore(@PathVariable UUID storeId, @RequestHeader(name = "X-User-Name") String userName) {
        return storeService.deleteStore(storeId, userName);
    }
}
