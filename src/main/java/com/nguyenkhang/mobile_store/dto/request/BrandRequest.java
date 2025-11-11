package com.nguyenkhang.mobile_store.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BrandRequest {

    String name;
    String description;


}
