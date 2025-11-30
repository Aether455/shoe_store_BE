package com.nguyenkhang.mobile_store.dto.response.vouchers;

import java.time.LocalDateTime;

import com.nguyenkhang.mobile_store.dto.response.user.SimpleUserResponse;
import com.nguyenkhang.mobile_store.dto.response.user.UserResponse;
import com.nguyenkhang.mobile_store.enums.VoucherStatus;
import com.nguyenkhang.mobile_store.enums.VoucherType;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class VoucherResponse {

    String id;
    String name;
    String voucherCode;

    VoucherType type;
    VoucherStatus status;

    double discountValue;

    double minApplicablePrice;
    double maxDiscountAmount;

    LocalDateTime startDate;
    LocalDateTime endDate;

    SimpleUserResponse createBy;
    SimpleUserResponse updateBy;

    LocalDateTime createAt;
    LocalDateTime updateAt;
}
