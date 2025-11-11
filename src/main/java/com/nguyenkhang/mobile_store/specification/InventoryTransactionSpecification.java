package com.nguyenkhang.mobile_store.specification;

import com.nguyenkhang.mobile_store.entity.*;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

public class InventoryTransactionSpecification {
    public static Specification<InventoryTransaction> createSpecification(String keyword){
        return ((root, query, criteriaBuilder) -> {
            if (!StringUtils.hasText(keyword))
                return criteriaBuilder.conjunction();//= where 1=1

            String likePattern = "%"+keyword+"%";

            Join<InventoryTransaction, User> userJoin = root.join("user", jakarta.persistence.criteria.JoinType.LEFT);
            Join<InventoryTransaction, Supplier> supplierJoin = root.join("supplier", JoinType.LEFT);
            Join<InventoryTransaction, Warehouse> warehouseJoin = root.join("warehouse",JoinType.LEFT);

            Predicate usernameLike = criteriaBuilder.like(userJoin.get("username"), likePattern);


            Predicate supplierNameLike = criteriaBuilder.like(supplierJoin.get("name"), likePattern);
            Predicate warehouseNameLike = criteriaBuilder.like(warehouseJoin.get("name"), likePattern);

            return criteriaBuilder.or(supplierNameLike,warehouseNameLike,usernameLike);
        });
    }
}
