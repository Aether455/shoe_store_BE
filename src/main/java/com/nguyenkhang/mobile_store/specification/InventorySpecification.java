package com.nguyenkhang.mobile_store.specification;

import com.nguyenkhang.mobile_store.dto.request.InventoryCriteria;
import com.nguyenkhang.mobile_store.entity.*;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class InventorySpecification {
    public static Specification<Inventory> createSpecification(InventoryCriteria criteria) {
        return ((root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (criteria.getWarehouseId() != null) {
                predicates.add(criteriaBuilder.equal(root.get("warehouse").get("id"), criteria.getWarehouseId()));
            }

            if (criteria.getCreateAtStart() != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("createAt"), criteria.getCreateAtStart()));
            }
            if (criteria.getCreateAtEnd() != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("createAt"), criteria.getCreateAtEnd()));
            }


            if (StringUtils.hasText(criteria.getKeyword())) {
                String likePattern = "%" + criteria.getKeyword().toLowerCase() + "%";
                List<Predicate> searchPredicates = new ArrayList<>();

                Join<Inventory, Warehouse> warehouseJoin = root.join("warehouse", JoinType.LEFT);

                Join<Inventory, ProductVariant> variantJoin = root.join("productVariant", JoinType.LEFT);

                Join<ProductVariant, Product> productJoin = variantJoin.join("product", JoinType.LEFT);


                searchPredicates.add(criteriaBuilder.like(root.get("id").as(String.class), likePattern));

                searchPredicates.add(criteriaBuilder.like(criteriaBuilder.lower(warehouseJoin.get("name")), likePattern));

                searchPredicates.add(criteriaBuilder.like(criteriaBuilder.lower(variantJoin.get("sku")), likePattern));

                searchPredicates.add(criteriaBuilder.like(criteriaBuilder.lower(productJoin.get("name")), likePattern));

                // Gộp nhóm search bằng OR và thêm vào danh sách chính (AND)
                predicates.add(criteriaBuilder.or(searchPredicates.toArray(new Predicate[0])));

                // Thêm distinct để tránh duplicate dữ liệu khi join nhiều bảng
                query.distinct(true);
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        });
    }
}
