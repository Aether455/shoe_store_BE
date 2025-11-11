package com.nguyenkhang.mobile_store.specification;

import com.nguyenkhang.mobile_store.entity.Customer;
import com.nguyenkhang.mobile_store.entity.Order;
import com.nguyenkhang.mobile_store.entity.User;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

public class CustomerSpecification {
    public static Specification<Customer> createSpecification(String keyword){
        return ((root, query, criteriaBuilder) -> {
            if (!StringUtils.hasText(keyword))
                return criteriaBuilder.conjunction();//= where 1=1

            String likePattern = "%"+keyword+"%";

            Join<Customer, User> userJoin = root.join("user", jakarta.persistence.criteria.JoinType.LEFT);

            Predicate usernameLike = criteriaBuilder.like(userJoin.get("username"), likePattern);
            Predicate phoneLike = criteriaBuilder.like(root.get("phoneNumber"), likePattern);
            Predicate fullNameLike = criteriaBuilder.like(criteriaBuilder.lower(root.get("fullName")), likePattern);

            return criteriaBuilder.or(phoneLike,fullNameLike,usernameLike);
        });
    }
}
