package com.nguyenkhang.mobile_store.controller;

import java.util.List;

import jakarta.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import com.nguyenkhang.mobile_store.dto.ApiResponse;
import com.nguyenkhang.mobile_store.dto.request.VoucherRequest;
import com.nguyenkhang.mobile_store.dto.response.vouchers.VoucherResponse;
import com.nguyenkhang.mobile_store.service.VoucherService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping("/vouchers")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class VoucherController {
    VoucherService voucherService;

    @GetMapping
    public ApiResponse<Page<VoucherResponse>> getVouchers( @RequestParam(defaultValue = "0") int page,
                                                           @RequestParam(defaultValue = "10") int size,
                                                           @RequestParam(defaultValue = "id") String sortBy) {
        return ApiResponse.<Page<VoucherResponse>>builder()
                .result(voucherService.getVouchers(page,size,sortBy))
                .build();
    }

    @PostMapping
    public ApiResponse<VoucherResponse> create(@RequestBody @Valid VoucherRequest request) {
        return ApiResponse.<VoucherResponse>builder()
                .result(voucherService.create(request))
                .build();
    }

    @GetMapping("/{voucherId}")
    public ApiResponse<VoucherResponse> getVoucherById(@PathVariable long voucherId) {
        return ApiResponse.<VoucherResponse>builder()
                .result(voucherService.getVoucherById(voucherId))
                .build();
    }

    @PutMapping("/{voucherId}")
    public ApiResponse<VoucherResponse> update(
            @PathVariable long voucherId, @RequestBody @Valid VoucherRequest request) {
        return ApiResponse.<VoucherResponse>builder()
                .result(voucherService.updateVoucher(voucherId, request))
                .build();
    }

    @DeleteMapping("/{voucherId}")
    ApiResponse<String> delete(@PathVariable long voucherId) {
        voucherService.delete(voucherId);
        return ApiResponse.<String>builder()
                .result("Voucher has been deleted")
                .build();
    }

    @GetMapping("/search")
    public ApiResponse<Page<VoucherResponse>> searchVouchers(
            @RequestParam(defaultValue = "0") int page, @RequestParam String keyword) {
        return ApiResponse.<Page<VoucherResponse>>builder()
                .result(voucherService.searchVouchers(keyword, page))
                .build();
    }
}
