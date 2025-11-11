package com.nguyenkhang.mobile_store.dto.response.vouchers;

import com.nguyenkhang.mobile_store.dto.response.user.UserResponse;
import com.nguyenkhang.mobile_store.enums.VoucherStatus;
import com.nguyenkhang.mobile_store.enums.VoucherType;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

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


    UserResponse createBy;
    UserResponse updateBy;

    LocalDateTime createAt;
    LocalDateTime updateAt;

}
