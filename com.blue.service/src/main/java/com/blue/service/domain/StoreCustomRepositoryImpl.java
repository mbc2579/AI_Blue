package com.blue.service.domain;

import com.blue.service.domain.order.Order;
import com.blue.service.domain.order.OrderCustomRepository;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.support.PageableExecutionUtils;

import java.util.List;

import static com.blue.service.config.QueryDslUtil.getAllOrderSpecifiers;
import static com.blue.service.domain.QStore.store;
import static com.blue.service.domain.order.QOrder.order;

@RequiredArgsConstructor
public class StoreCustomRepositoryImpl implements StoreCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<Store> searchByStoreName(String userName,
                                         String storeName,
                                         String keyword,
                                         Pageable pageable) {
        // 정렬 조건 가져오기
        List<OrderSpecifier> orderSpecifiers = getAllOrderSpecifiers(pageable, "store");

        // 쿼리 생성 및 페이징 처리
        List<Store> stores = jpaQueryFactory
                .selectFrom(store)
                .distinct()
                .where(
                        store.userName.eq(userName),
                        store.deletedAt.isNull(), // 삭제된 가게 제외
                        store.storeName.containsIgnoreCase(storeName), // 가게명 필터링
                        store.storeDescription.containsIgnoreCase(keyword) // 설명에 keyword 필터링
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(orderSpecifiers.toArray(new OrderSpecifier[0])) // 정렬 조건 배열로 변환
                .fetch();

        // 전체 카운트 조회 (페이징을 위한 총 레코드 수)
        JPAQuery<Long> countQuery = jpaQueryFactory
                .select(store.count())
                .from(store)
                .where(
                        store.userName.eq(userName),
                        store.deletedAt.isNull(), // 삭제된 가게 제외
                        store.storeName.containsIgnoreCase(storeName), // 가게명 필터링
                        store.storeDescription.containsIgnoreCase(keyword) // 설명에 keyword 필터링
                );

        return PageableExecutionUtils.getPage(stores, pageable, countQuery::fetchOne);
    }
}