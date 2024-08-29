package com.blue.service.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface StoreRepository extends JpaRepository<Store, UUID> {
    // 삭제되지 않은 가게만 상세조회
    Optional<Store> findByStoreIdAndDeletedAtIsNull(UUID storeId);
    // 삭제되지 않은 가게만 전체조회
    List<Store> findAllByDeletedAtIsNull();
}
