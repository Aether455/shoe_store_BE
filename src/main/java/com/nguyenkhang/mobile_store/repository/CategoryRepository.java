package com.nguyenkhang.mobile_store.repository;

import com.nguyenkhang.mobile_store.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Long>  {
    
}
