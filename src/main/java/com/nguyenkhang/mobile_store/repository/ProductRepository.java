package com.nguyenkhang.mobile_store.repository;

import com.nguyenkhang.mobile_store.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Page<Product> findAllByCategoryId(long categoryId, Pageable pageable);
    Page<Product> findAllByBrandId(long brandId,  Pageable pageable);
}
