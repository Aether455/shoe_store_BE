package com.nguyenkhang.mobile_store.controller;

import com.nguyenkhang.mobile_store.dto.ApiResponse;
import com.nguyenkhang.mobile_store.dto.request.VoucherRequest;
import com.nguyenkhang.mobile_store.dto.response.SupplierResponse;
import com.nguyenkhang.mobile_store.dto.response.vouchers.VoucherResponse;
import com.nguyenkhang.mobile_store.service.VoucherService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vouchers")
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@RequiredArgsConstructor
public class VoucherController {
    VoucherService voucherService;

    @GetMapping
    public ApiResponse<List<VoucherResponse>> getVouchers(){
        return ApiResponse.<List<VoucherResponse>>builder().result(voucherService.getVouchers()).message("Success!").build();
    }

    @PostMapping
    public ApiResponse<VoucherResponse> create(@RequestBody @Valid VoucherRequest request){
        return ApiResponse.<VoucherResponse>builder().result(voucherService.create(request)).message("Success!").build();
    }
    @GetMapping("/{voucherId}")
    public ApiResponse<VoucherResponse> getVoucherById(@PathVariable long voucherId){
        return ApiResponse.<VoucherResponse>builder().result(voucherService.getVoucherById(voucherId)).build();
    }
    @PutMapping("/{voucherId}")
    public ApiResponse<VoucherResponse> update(@PathVariable long voucherId, @RequestBody @Valid VoucherRequest request){
        return ApiResponse.<VoucherResponse>builder().result(voucherService.updateVoucher(voucherId,request)).build();
    }
    @DeleteMapping("/{voucherId}")
    ApiResponse<String> delete(@PathVariable long voucherId) {
        voucherService.delete(voucherId);
        return ApiResponse.<String>builder().message("Success!").result("Voucher has been deleted").build();
    }

    @GetMapping("/search")
    public ApiResponse<Page<VoucherResponse>> searchVouchers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam String keyword){
        return ApiResponse.<Page<VoucherResponse>>builder().result(voucherService.searchVouchers(keyword, page)).build();
    }
}
