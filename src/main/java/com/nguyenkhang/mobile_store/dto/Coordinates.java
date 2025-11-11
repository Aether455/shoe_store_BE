package com.nguyenkhang.mobile_store.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Coordinates {
    Double latitude;
    Double longitude;
}
