package com.blue.service.domain.order;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, UUID>, OrderCustomRepository {
    Optional<Order> findOneByOrderIdAndDeletedAtIsNull(UUID orderId);
}
