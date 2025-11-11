package com.nguyenkhang.mobile_store.repository;

import com.nguyenkhang.mobile_store.entity.Voucher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface VoucherRepository extends JpaRepository<Voucher,Long>, JpaSpecificationExecutor<Voucher> {

}
