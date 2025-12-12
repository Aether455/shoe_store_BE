package com.nguyenkhang.shoe_store.service;

import jakarta.persistence.EntityManager;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nguyenkhang.shoe_store.dto.request.voucher.VoucherRequest;
import com.nguyenkhang.shoe_store.dto.response.vouchers.VoucherResponse;
import com.nguyenkhang.shoe_store.dto.response.vouchers.VoucherResponseForCustomer;
import com.nguyenkhang.shoe_store.entity.User;
import com.nguyenkhang.shoe_store.entity.Voucher;
import com.nguyenkhang.shoe_store.exception.AppException;
import com.nguyenkhang.shoe_store.exception.ErrorCode;
import com.nguyenkhang.shoe_store.mapper.VoucherMapper;
import com.nguyenkhang.shoe_store.repository.VoucherRepository;
import com.nguyenkhang.shoe_store.specification.VoucherSpecification;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class VoucherService {
    VoucherRepository voucherRepository;
    VoucherMapper voucherMapper;
    UserService userService;
    EntityManager entityManager;

    @Transactional
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_USER')")
    public VoucherResponse create(VoucherRequest request) {
        User user = userService.getCurrentUser();

        Voucher voucher = voucherMapper.toVoucher(request);
        voucher.setCreateBy(user);
        try {
            voucherRepository.save(voucher);
        } catch (DataIntegrityViolationException exception) {
            throw new AppException(ErrorCode.VOUCHER_EXISTED);
        }

        return voucherMapper.toVoucherResponse(voucher);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_USER')")
    public Page<VoucherResponse> getVouchers(int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy).ascending());
        Page<Voucher> vouchers = voucherRepository.findAll(pageable);

        return vouchers.map((voucherMapper::toVoucherResponse));
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_USER')")
    public VoucherResponse getVoucherById(long voucherId) {
        Voucher voucher = voucherRepository
                .findById(voucherId)
                .orElseThrow(() -> new AppException(ErrorCode.VOUCHER_NOT_EXISTED));

        return voucherMapper.toVoucherResponse(voucher);
    }
    // dành cho khi tạo đơn hàng mới call
    public VoucherResponseForCustomer findByVoucherCode(String voucherCode) {
        Voucher voucher = voucherRepository
                .findByVoucherCode(voucherCode)
                .orElseThrow(() -> new AppException(ErrorCode.VOUCHER_NOT_EXISTED));

        return voucherMapper.toVoucherResponseForCustomer(voucher);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_USER')")
    public VoucherResponse updateVoucher(long voucherId, VoucherRequest request) {
        User user = userService.getCurrentUser();

        Voucher voucher = voucherRepository
                .findById(voucherId)
                .orElseThrow(() -> new AppException(ErrorCode.VOUCHER_NOT_EXISTED));

        voucherMapper.updateVoucher(voucher, request);
        voucher.setUpdateBy(user);
        voucher = voucherRepository.save(voucher);

        return voucherMapper.toVoucherResponse(voucher);
    }

    @Transactional(rollbackFor = ConstraintViolationException.class)
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public void delete(long voucherId) {
        Voucher voucher = voucherRepository
                .findById(voucherId)
                .orElseThrow(() -> new AppException(ErrorCode.VOUCHER_NOT_EXISTED));
        try {
            voucherRepository.delete(voucher);
            entityManager.flush();
        } catch (ConstraintViolationException e) {
            throw new AppException(ErrorCode.CANNOT_DELETE_VOUCHER_LINKED_ORDER);
        }
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_USER')")
    public Page<VoucherResponse> searchVouchers(String keyword, int page) {
        Pageable pageable = PageRequest.of(page, 20);
        return voucherRepository
                .findAll(VoucherSpecification.createSpecification(keyword), pageable)
                .map(voucherMapper::toVoucherResponse);
    }

    public Page<VoucherResponseForCustomer> searchVouchersForUser(String keyword, int page) {
        Pageable pageable = PageRequest.of(page, 20);
        return voucherRepository
                .findAll(VoucherSpecification.createSpecification(keyword), pageable)
                .map(voucherMapper::toVoucherResponseForCustomer);
    }
}
