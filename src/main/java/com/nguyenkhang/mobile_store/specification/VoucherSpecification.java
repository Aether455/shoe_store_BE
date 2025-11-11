package com.nguyenkhang.mobile_store.specification;

import com.nguyenkhang.mobile_store.entity.Supplier;
import com.nguyenkhang.mobile_store.entity.Voucher;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

public class VoucherSpecification {
    public static Specification<Voucher> createSpecification(String keyword){
        return ((root, query, criteriaBuilder) -> {
            if (!StringUtils.hasText(keyword))
                return criteriaBuilder.conjunction();//= where 1=1

            String likePattern = "%"+keyword+"%";


            Predicate voucherCodeLike = criteriaBuilder.like(root.get("voucherCode"), likePattern);
            Predicate nameLike = criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), likePattern);

            return criteriaBuilder.or(voucherCodeLike,nameLike);
        });
    }
}
