package com.nguyenkhang.mobile_store.dto.response.brand;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BrandResponseForCustomer {

    String id;
    String name;
    String description;

}
