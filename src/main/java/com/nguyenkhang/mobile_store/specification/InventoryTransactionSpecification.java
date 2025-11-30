package com.nguyenkhang.mobile_store.specification;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import com.nguyenkhang.mobile_store.entity.*;

public class InventoryTransactionSpecification {
    public static Specification<InventoryTransaction> createSpecification(String keyword) {
        return ((root, query, criteriaBuilder) -> {
            if (!StringUtils.hasText(keyword)) return criteriaBuilder.conjunction(); // = where 1=1

            String likePattern = "%" + keyword + "%";

            Join<InventoryTransaction, User> userJoin = root.join("createBy", jakarta.persistence.criteria.JoinType.LEFT);
            Join<InventoryTransaction, Product> productJoin = root.join("product", JoinType.LEFT);
            Join<InventoryTransaction, Warehouse> warehouseJoin = root.join("warehouse", JoinType.LEFT);

            Predicate usernameLike = criteriaBuilder.like(userJoin.get("username"), likePattern);

            Predicate productNameLike = criteriaBuilder.like(productJoin.get("name"), likePattern);
            Predicate warehouseNameLike = criteriaBuilder.like(warehouseJoin.get("name"), likePattern);

            return criteriaBuilder.or(productNameLike, warehouseNameLike, usernameLike);
        });
    }
}
