package com.nguyenkhang.mobile_store.enums;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum PaymentMethod {
    CASH("Cash"),
    BANK_TRANSFER("Bank transfer")
    ;



    String displayName;
}
