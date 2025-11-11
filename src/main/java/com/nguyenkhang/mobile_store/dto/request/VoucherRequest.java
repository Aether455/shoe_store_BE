package com.nguyenkhang.mobile_store.dto.request;

import com.nguyenkhang.mobile_store.enums.VoucherStatus;
import com.nguyenkhang.mobile_store.enums.VoucherType;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;

import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;
import lombok.experimental.FieldDefaults;


import java.time.LocalDateTime;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class VoucherRequest {

    @NotBlank(message = "VOUCHER_NAME_REQUIRED")
    String name;
    @NotBlank(message = "VOUCHER_CODE_REQUIRED")
    String voucherCode;


    VoucherType type;


    VoucherStatus status;

    @Positive(message = "VOUCHER_DISCOUNT_INVALID")
    double discountValue;

    @PositiveOrZero(message = "VOUCHER_MIN_PRICE_INVALID")
    double minApplicablePrice;
    @PositiveOrZero(message = "VOUCHER_MAX_DISCOUNT_INVALID")
    double maxDiscountAmount;


    LocalDateTime startDate;

    @Future(message = "VOUCHER_END_DATE_INVALID")
    LocalDateTime endDate;

}
