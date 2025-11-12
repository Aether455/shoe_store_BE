package com.nguyenkhang.mobile_store.repository;

import com.nguyenkhang.mobile_store.entity.Voucher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface VoucherRepository extends JpaRepository<Voucher,Long>, JpaSpecificationExecutor<Voucher> {
    Optional<Voucher> findByVoucherCode(String voucherCode);
}
