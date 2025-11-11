package com.nguyenkhang.mobile_store.enums;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum VoucherStatus {
    ACTIVE("Active"),
    EXPIRED("Expired"),
    INACTIVE("Inactive")

    ;

    String displayName;
}
