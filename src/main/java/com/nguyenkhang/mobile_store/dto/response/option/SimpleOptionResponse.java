package com.nguyenkhang.mobile_store.dto.response.option;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SimpleOptionResponse {
    Long id;

    String name;

    List<SimpleOptionValueResponse> optionValues;


}
