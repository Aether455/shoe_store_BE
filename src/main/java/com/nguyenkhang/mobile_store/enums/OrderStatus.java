package com.nguyenkhang.mobile_store.enums;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum OrderStatus {
    PENDING("Pending"),
    CONFIRMED("Confirmed"),
    DELIVERING("Delivering"),
    DELIVERED("Delivered"),
    COMPLETED("Completed"),
    CANCELLED("Canceled")
;
    String displayName;

    }
