package com.blue.service.domain.payment;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import java.util.List;

import static com.blue.service.config.QueryDslUtil.getAllOrderSpecifiers;

@RequiredArgsConstructor
public class PaymentCustomRepositoryImpl implements PaymentCustomRepository{

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<Payment> searchByUserName(String userName, Pageable pageable) {
        List<OrderSpecifier> orders = getAllOrderSpecifiers(pageable, "payment");

        List<Payment> query = jpaQueryFactory
                .selectFrom(QPayment.payment)
                .distinct()
                .where(
                        QPayment.payment.userName.eq(userName),
                        QPayment.payment.deletedAt.isNull()
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(orders.stream().toArray(OrderSpecifier[]::new))
                .fetch();

        JPAQuery<Long> countQuery = jpaQueryFactory
                .select(QPayment.payment.count())
                .from(QPayment.payment)
                .where(
                        QPayment.payment.userName.eq(userName),
                        QPayment.payment.deletedAt.isNull()
                );

        return PageableExecutionUtils.getPage(query, pageable, () -> countQuery.fetchCount());
    }
}
