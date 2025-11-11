package com.nguyenkhang.mobile_store.repository;

import com.nguyenkhang.mobile_store.entity.Staff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface StaffRepository extends JpaRepository<Staff,Long>, JpaSpecificationExecutor<Staff> {
    boolean existsByPhoneNumber(String phoneNUmber);
}
