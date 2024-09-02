package com.blue.service.domain.product;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import java.util.List;

import static com.blue.service.config.QueryDslUtil.getAllOrderSpecifiers;
import static com.blue.service.domain.product.QProduct.product;
import static com.blue.service.domain.store.QStore.store;

@Slf4j
@RequiredArgsConstructor
public class ProductCustomRepositoryImpl implements ProductCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<Product> searchByProductName(String userName,
                                             String productName,
                                             String keyword,
                                             Pageable pageable) {
        // 정렬 조건 가져오기
        List<OrderSpecifier> orderSpecifiers = getAllOrderSpecifiers(pageable, "product");

        // 쿼리 생성 및 페이징 처리
        List<Product> products = jpaQueryFactory
                .selectFrom(product)
                .join(product.store, store) // store와 join
                .distinct()
                .where(
                        store.userName.eq(userName),
                        store.deletedAt.isNull(), // 삭제된 가게 제외
                        product.productName.containsIgnoreCase(productName), // 제품명 필터링
                        store.storeName.containsIgnoreCase(keyword) // 가게 이름을 키워드로
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(orderSpecifiers.toArray(new OrderSpecifier[0])) // 정렬 조건 배열로 변환
                .fetch();

        // 전체 카운트 조회 (페이징을 위한 총 레코드 수)
        JPAQuery<Long> countQuery = jpaQueryFactory
                .select(product.count())
                .from(product)
                .join(product.store, store) // store와 join
                .where(
                        store.userName.eq(userName),
                        store.deletedAt.isNull(), // 삭제된 가게 제외
                        product.productName.containsIgnoreCase(productName), // 제품명 필터링
                        store.storeName.containsIgnoreCase(keyword) // 가게 설명에 keyword 필터링
                );

        return PageableExecutionUtils.getPage(products, pageable, countQuery::fetchOne);
    }
}