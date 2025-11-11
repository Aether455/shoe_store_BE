package com.nguyenkhang.mobile_store.repository;

import com.nguyenkhang.mobile_store.entity.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BrandRepository extends JpaRepository<Brand, Long> {
}
