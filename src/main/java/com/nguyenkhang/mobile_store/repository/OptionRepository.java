package com.nguyenkhang.mobile_store.repository;

import com.nguyenkhang.mobile_store.entity.Option;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OptionRepository extends JpaRepository<Option, Long> {
    boolean existsByName(String name);
}
