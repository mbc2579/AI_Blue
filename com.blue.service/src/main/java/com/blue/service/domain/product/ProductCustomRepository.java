package com.blue.service.domain.product;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductCustomRepository {
    Page<Product> searchByProductName(String userName, String productName, String keyword, Pageable pageable);
}
