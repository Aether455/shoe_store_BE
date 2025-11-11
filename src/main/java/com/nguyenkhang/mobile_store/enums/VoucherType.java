package com.nguyenkhang.mobile_store.enums;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum VoucherType {
    PERCENTAGE("Reduced by percentage"),
    FIXED_AMOUNT("Fixed amount reduction")
    ;

    String displayName;


}
