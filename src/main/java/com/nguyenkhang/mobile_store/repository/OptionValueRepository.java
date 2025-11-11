package com.nguyenkhang.mobile_store.repository;

import com.nguyenkhang.mobile_store.entity.OptionValue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OptionValueRepository extends JpaRepository<OptionValue, Long> {
    boolean existsByValue(String value);

    List<OptionValue> findAllByOptionId(long optionId);
}
