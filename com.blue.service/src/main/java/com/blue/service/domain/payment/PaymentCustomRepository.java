package com.blue.service.domain.payment;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PaymentCustomRepository {
    Page<Payment> searchByUserName(String userName, Pageable pageable);
}
