package com.blue.service.domain.order;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import java.util.List;

import static com.blue.service.config.QueryDslUtil.getAllOrderSpecifiers;
import static com.blue.service.domain.order.QOrder.order;

@RequiredArgsConstructor
public class OrderCustomRepositoryImpl implements OrderCustomRepository{

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<Order> searchByUserName(String userName,
                                        String keyword,
                                        Pageable pageable) {
        List<OrderSpecifier> orders = getAllOrderSpecifiers(pageable, "order");

        List<Order> query = jpaQueryFactory
                .selectFrom(order)
                .distinct()
                .where(
                        order.userName.eq(userName),
                        order.deletedAt.isNull()
                        //가게명 조건 추가 필요 가게명 contains keyword
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(orders.stream().toArray(OrderSpecifier[]::new))
                .fetch();

        JPAQuery<Long> countQuery = jpaQueryFactory
                .select(order.count())
                .from(order)
                .where(
                        order.userName.eq(userName),
                        order.deletedAt.isNull()
                );

        return PageableExecutionUtils.getPage(query, pageable, () -> countQuery.fetchOne());
    }
}
