package com.nguyenkhang.mobile_store.repository;

import com.nguyenkhang.mobile_store.entity.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface SupplierRepository extends JpaRepository<Supplier,Long>, JpaSpecificationExecutor<Supplier> {
    boolean existsByPhoneNumber(String phoneNumber);
    boolean existsByPhoneNumberAndIdNot(String phoneNumber,long id);
}
