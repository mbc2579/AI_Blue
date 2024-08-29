package com.blue.service.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {
    // 삭제되지 않은 메뉴만 상세조회
    Optional<Product> findByProductIdAndDeletedAtIsNull(UUID productId);
    // 삭제되지 않은 메뉴만 전체조회
    List<Product> findAllByStore_StoreIdAndDeletedAtIsNullOrderByCreatedAt(UUID storeId);
    // isVisible이 true 이고 deletedAt가 null인 메뉴만 조회
    List<Product> findAllByStore_StoreIdAndDeletedAtIsNullAndIsVisibleTrueOrderByCreatedAt(UUID storeId);
}
