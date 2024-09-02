package com.blue.service.config;

import com.blue.service.domain.QProduct;
import com.blue.service.domain.QStore;
import com.blue.service.domain.order.QOrder;
import com.blue.service.domain.payment.QPayment;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.Expressions;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;

@Slf4j(topic = "QueryDslUtil")
public class QueryDslUtil {

    public static OrderSpecifier<?> getSortedColumn(Order order, Path<?> parent, String fieldName){
        Path<Object> fieldPath = Expressions.path(Object.class, parent, fieldName);

        return new OrderSpecifier(order, fieldPath);
    }

    public static List<OrderSpecifier> getAllOrderSpecifiers(Pageable pageable, String entityType){
        List<OrderSpecifier> orders = new ArrayList<>();

        if (!pageable.getSort().isEmpty()){
            for(Sort.Order order : pageable.getSort()){
                if("createdAt".equals(order.getProperty())){
                    Order direction = order.getDirection().isAscending() ? Order.ASC : Order.DESC;
                    Path path;
                    switch (entityType){
                        case "order":
                            path = QOrder.order;
                            break;
                        case "payment":
                            path = QPayment.payment;
                            break;
                        case "store" :
                            path = QStore.store;
                            break;
                        case "product" :
                            path = QProduct.product;
                            break;
                        default:
                            throw new IllegalArgumentException("Entity의 타입이 잘못되었음");
                    }
                    OrderSpecifier<?> orderSpecifier = QueryDslUtil.getSortedColumn(direction, path, "createdAt");
                    orders.add(orderSpecifier);
                } else if ("modifiedAt".equals(order.getProperty())) {
                    Order direction = order.getDirection().isAscending() ? Order.ASC : Order.DESC;
                    Path path;
                    switch (entityType){
                        case "order":
                            path = QOrder.order;
                            break;
                        case "payment":
                            path = QPayment.payment;
                            break;
                        case "store" :
                            path = QStore.store;
                            break;
                        case "product" :
                            path = QProduct.product;
                            break;
                        default:
                            throw new IllegalArgumentException("Entity의 타입이 잘못되었음");
                    }
                    OrderSpecifier<?> orderSpecifier = QueryDslUtil.getSortedColumn(direction, path, "modifiedAt");
                    orders.add(orderSpecifier);
                }

            }
        }

        return orders;
    }
}
