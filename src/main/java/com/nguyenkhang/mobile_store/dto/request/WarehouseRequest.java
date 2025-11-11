package com.nguyenkhang.mobile_store.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class WarehouseRequest {

    @NotBlank(message = "WAREHOUSE_NAME_REQUIRED")
    String name;
    @NotBlank(message = "ADDRESS_REQUIRED")
    String address;
    @NotNull(message = "PRIORITY_REQUIRED")
    int priority;
    @NotBlank(message = "PROVINCE_REQUIRED")
    String province;

    @NotBlank(message = "DISTRICT_REQUIRED")
    String district;

    @NotBlank(message = "WARD_REQUIRED")
    String ward;

    String description;

}
