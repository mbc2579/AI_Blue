package com.blue.service.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ReviewRepository extends JpaRepository<Review, UUID> {
    List<Review> findAllByStore_StoreIdAndDeletedAtIsNullOrderByCreatedAt(UUID storeId);
}
