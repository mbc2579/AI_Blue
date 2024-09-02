package com.blue.service.domain.order;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderCustomRepository {
    Page<Order> searchByUserName(String userName, String keyword, Pageable pageable);
}
