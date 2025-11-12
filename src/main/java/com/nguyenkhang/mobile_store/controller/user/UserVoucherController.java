package com.nguyenkhang.mobile_store.controller.user;

import com.nguyenkhang.mobile_store.dto.ApiResponse;
import com.nguyenkhang.mobile_store.dto.request.VoucherRequest;
import com.nguyenkhang.mobile_store.dto.response.vouchers.VoucherResponse;
import com.nguyenkhang.mobile_store.dto.response.vouchers.VoucherResponseForCustomer;
import com.nguyenkhang.mobile_store.service.VoucherService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user/vouchers")
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@RequiredArgsConstructor
public class UserVoucherController {
    VoucherService voucherService;

    @GetMapping("/{voucherCode}")
    public ApiResponse<VoucherResponseForCustomer> getByVoucherCode(@PathVariable String voucherCode){
        return ApiResponse.<VoucherResponseForCustomer>builder().result(voucherService.findByVoucherCode(voucherCode)).build();
    }

    @GetMapping("/search")
    public ApiResponse<Page<VoucherResponseForCustomer>> searchVouchers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam String keyword){
        return ApiResponse.<Page<VoucherResponseForCustomer>>builder().result(voucherService.searchVouchersForUser(keyword, page)).build();
    }
}
