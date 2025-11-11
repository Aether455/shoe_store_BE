package com.nguyenkhang.mobile_store.specification;

import com.nguyenkhang.mobile_store.entity.Customer;
import com.nguyenkhang.mobile_store.entity.User;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

public class UserSpecification {
    public static Specification<User> createSpecification(String keyword){
        return ((root, query, criteriaBuilder) -> {
            if (!StringUtils.hasText(keyword))
                return criteriaBuilder.conjunction();//= where 1=1

            String likePattern = "%"+keyword+"%";


            Predicate usernameLike = criteriaBuilder.like(root.get("username"), likePattern);
            Predicate emailLike = criteriaBuilder.like(criteriaBuilder.lower(root.get("email")), likePattern);

            return criteriaBuilder.or(emailLike,usernameLike);
        });
    }
}
