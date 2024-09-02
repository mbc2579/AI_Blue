package com.blue.service.domain.payment;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface PaymentRepository extends JpaRepository<Payment, UUID>, PaymentCustomRepository {
    Optional<Payment> findOneByPaymentIdAndDeletedAtIsNull(UUID paymentId);
}
