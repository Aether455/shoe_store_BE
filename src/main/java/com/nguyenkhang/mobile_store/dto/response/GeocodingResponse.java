package com.nguyenkhang.mobile_store.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GeocodingResponse {
    @JsonProperty("lat")
    String latitude;
    @JsonProperty("lon")
    String longitude;
}
