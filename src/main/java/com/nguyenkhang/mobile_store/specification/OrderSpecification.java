package com.nguyenkhang.mobile_store.specification;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.criteria.Predicate;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import com.nguyenkhang.mobile_store.dto.request.order.OrderCriteria;
import com.nguyenkhang.mobile_store.entity.Order;

public class OrderSpecification {
    public static Specification<Order> createSpecification(OrderCriteria criteria) {
        return ((root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (criteria.getMinFinalAmount() != null) {
                predicates.add(
                        criteriaBuilder.greaterThanOrEqualTo(root.get("finalAmount"), criteria.getMinFinalAmount()));
            }

            if (criteria.getMaxFinalAmount() != null) {
                predicates.add(
                        criteriaBuilder.lessThanOrEqualTo(root.get("finalAmount"), criteria.getMaxFinalAmount()));
            }

            if(criteria.getStatus() !=null){
                predicates.add(criteriaBuilder.equal(root.get("status"),criteria.getStatus()));
            }

            if (criteria.getCreateAtBegin() != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("createAt"), criteria.getCreateAtBegin()));
            }

            if (criteria.getCreateAtEnd() != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("createAt"), criteria.getCreateAtEnd()));
            }

            if (StringUtils.hasText(criteria.getKeyword())) {
                String likePattern = "%" + criteria.getKeyword().toLowerCase() + "%";
                List<Predicate> searchPredicates = new ArrayList<>();

                searchPredicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("orderCode")), likePattern));
                searchPredicates.add(criteriaBuilder.like(root.get("phoneNumber"), likePattern));
                searchPredicates.add(
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("receiverName")), likePattern));

                predicates.add(criteriaBuilder.or(searchPredicates.toArray(new Predicate[0])));
                // Thêm distinct để tránh duplicate dữ liệu khi join nhiều bảng
                query.distinct(true);
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        });
    }
}
