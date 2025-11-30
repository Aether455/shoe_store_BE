package com.nguyenkhang.mobile_store.specification;

import com.nguyenkhang.mobile_store.dto.request.products.ProductSearchCriteria;
import com.nguyenkhang.mobile_store.entity.Product;
import com.nguyenkhang.mobile_store.entity.ProductVariant;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class ProductSpecification {
    public static Specification<Product> withCriteria(ProductSearchCriteria criteria) {
        return ((root, query, criteriaBuilder) -> {
            // List để chứa tất cả các điều kiện (Predicate)
            List<Predicate> predicates = new ArrayList<>();

            //Lọc trên bảng product
            if (criteria.getProductName() !=null && !criteria.getProductName().isEmpty()){
                predicates.add(criteriaBuilder.like(root.get("name"),"%"+criteria.getProductName()+"%"));
            }


            if (criteria.getCategoryId() != null) {
                predicates.add(criteriaBuilder.equal(root.get("category").get("id"), criteria.getCategoryId()));
            }

            if (criteria.getBrandId() != null) {
                predicates.add(criteriaBuilder.equal(root.get("brand").get("id"), criteria.getBrandId()));
            }

            if (criteria.getCreateAtStart() != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("createAt"), criteria.getCreateAtStart()));
            }

            if (criteria.getCreateAtEnd() != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("createAt"), criteria.getCreateAtEnd()));
            }

            if (criteria.getSku() != null || criteria.getMinPrice() !=null || criteria.getMaxPrice() != null || criteria.getMinQuantity() != null){
                // Tạo một JOIN từ Product (root) đến productVariants
                // Dùng JoinType.LEFT để vẫn trả về Product dù không có variant nào khớp
                Join<Product, ProductVariant> variantJoin = root.join("productVariants", JoinType.LEFT);

                if (criteria.getSku() != null && !criteria.getSku().isEmpty()){
                    predicates.add(criteriaBuilder.like(root.get("productVariants").get("sku"),"%"+criteria.getSku()+"%"));
                }

                if (criteria.getMinPrice() != null) {
                    predicates.add(criteriaBuilder.greaterThanOrEqualTo(variantJoin.get("price"), criteria.getMinPrice()));
                }

                if (criteria.getMaxPrice() != null) {
                    predicates.add(criteriaBuilder.lessThanOrEqualTo(variantJoin.get("price"), criteria.getMaxPrice()));
                }

                if (criteria.getMinQuantity() != null) {
                    predicates.add(criteriaBuilder.greaterThanOrEqualTo(variantJoin.get("quantity"), criteria.getMinQuantity()));
                }

                query.distinct(true);
            }

            // Kết hợp tất cả các điều kiện lại bằng AND
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        });
    }
}
