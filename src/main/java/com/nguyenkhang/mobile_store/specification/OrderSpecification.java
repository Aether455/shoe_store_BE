package com.nguyenkhang.mobile_store.specification;


import com.nguyenkhang.mobile_store.entity.Order;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;


public class OrderSpecification {
    public static Specification<Order> createSpecification(String keyword){
        return ((root, query, criteriaBuilder) -> {
            if (!StringUtils.hasText(keyword))
                return criteriaBuilder.conjunction();//=where 1=1

            String likePattern = "%"+keyword+"%";

            Predicate orderCodeLike =  criteriaBuilder.like(criteriaBuilder.lower(root.get("orderCode")),likePattern);
            Predicate phoneLike = criteriaBuilder.like(root.get("phoneNumber"), likePattern);
            Predicate receiverNameLike = criteriaBuilder.like(criteriaBuilder.lower(root.get("receiverName")), likePattern);

            return criteriaBuilder.or(orderCodeLike,phoneLike,receiverNameLike);
        });
    }
}
