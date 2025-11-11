package com.nguyenkhang.mobile_store.specification;

import com.nguyenkhang.mobile_store.entity.*;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

public class PurchaseOrderSpecification {
    public static Specification<PurchaseOrder> createSpecification(String keyword){
        return ((root, query, criteriaBuilder) -> {
            if (!StringUtils.hasText(keyword))
                return criteriaBuilder.conjunction();//= where 1=1

            String likePattern = "%"+keyword+"%";

            Join<PurchaseOrder, Supplier> supplierJoin = root.join("supplier", JoinType.LEFT);
            Join<PurchaseOrder, Warehouse> warehouseJoin = root.join("warehouse",JoinType.LEFT);

            Predicate supplierNameLike = criteriaBuilder.like(supplierJoin.get("name"), likePattern);
            Predicate warehouseNameLike = criteriaBuilder.like(warehouseJoin.get("name"), likePattern);

            return criteriaBuilder.or(warehouseNameLike,supplierNameLike);
        });
    }
}
