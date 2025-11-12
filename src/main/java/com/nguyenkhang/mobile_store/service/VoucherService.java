package com.nguyenkhang.mobile_store.service;

import com.nguyenkhang.mobile_store.dto.request.VoucherRequest;
import com.nguyenkhang.mobile_store.dto.response.SupplierResponse;
import com.nguyenkhang.mobile_store.dto.response.vouchers.VoucherResponse;
import com.nguyenkhang.mobile_store.dto.response.vouchers.VoucherResponseForCustomer;
import com.nguyenkhang.mobile_store.entity.User;
import com.nguyenkhang.mobile_store.entity.Voucher;
import com.nguyenkhang.mobile_store.exception.AppException;
import com.nguyenkhang.mobile_store.exception.ErrorCode;
import com.nguyenkhang.mobile_store.mapper.VoucherMapper;
import com.nguyenkhang.mobile_store.repository.UserRepository;
import com.nguyenkhang.mobile_store.repository.VoucherRepository;
import com.nguyenkhang.mobile_store.specification.SupplierSpecification;
import com.nguyenkhang.mobile_store.specification.VoucherSpecification;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class VoucherService {
    VoucherRepository voucherRepository;
    VoucherMapper voucherMapper;
    UserRepository userRepository;

    @Transactional
    public VoucherResponse create(VoucherRequest request) {
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();
        User user = userRepository.findByUsername(name).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        Voucher voucher = voucherMapper.toVoucher(request);
        voucher.setCreateBy(user);
        try {
            voucherRepository.save(voucher);
        } catch (DataIntegrityViolationException exception) {
            throw new AppException(ErrorCode.VOUCHER_EXISTED);
        }


        return voucherMapper.toVoucherResponse(voucher);
    }

    public List<VoucherResponse> getVouchers() {
        List<Voucher> voucherList = voucherRepository.findAll();

        return voucherList.stream().map((voucherMapper::toVoucherResponse)).toList();
    }


    public VoucherResponse getVoucherById(long voucherId) {
        Voucher voucher = voucherRepository.findById(voucherId).orElseThrow(() -> new AppException(ErrorCode.VOUCHER_NOT_EXISTED));

        return voucherMapper.toVoucherResponse(voucher);
    }

    public VoucherResponseForCustomer findByVoucherCode(String voucherCode) {
        Voucher voucher = voucherRepository.findByVoucherCode(voucherCode).orElseThrow(() -> new AppException(ErrorCode.VOUCHER_NOT_EXISTED));

        return voucherMapper.toVoucherResponseForCustomer(voucher);
    }

    public VoucherResponse updateVoucher(long voucherId, VoucherRequest request) {
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();
        User user = userRepository.findByUsername(name).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        Voucher voucher = voucherRepository.findById(voucherId).orElseThrow(() -> new AppException(ErrorCode.VOUCHER_NOT_EXISTED));

        voucherMapper.updateVoucher(voucher, request);
        voucher.setUpdateBy(user);
        voucher = voucherRepository.save(voucher);


        return voucherMapper.toVoucherResponse(voucher);
    }

    public void delete(long voucherId) {
        voucherRepository.deleteById(voucherId);
    }

    public Page<VoucherResponse> searchVouchers(String keyword, int page){
        Pageable pageable = PageRequest.of(page, 20);
        return voucherRepository.findAll(VoucherSpecification.createSpecification(keyword), pageable).map(voucherMapper::toVoucherResponse);
    }

    public Page<VoucherResponseForCustomer> searchVouchersForUser(String keyword, int page){
        Pageable pageable = PageRequest.of(page, 20);
        return voucherRepository.findAll(VoucherSpecification.createSpecification(keyword), pageable).map(voucherMapper::toVoucherResponseForCustomer);
    }
}
