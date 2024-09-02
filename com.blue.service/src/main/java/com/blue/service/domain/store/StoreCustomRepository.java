package com.blue.service.domain.store;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface StoreCustomRepository {
    Page<Store> searchByStoreName(String userName, String storeName, String keyword, Pageable pageable);
}
