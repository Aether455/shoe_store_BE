package com.nguyenkhang.mobile_store.dto.request.option;

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
public class OptionValueRequest {
    @NotNull(message = "OPTION_ID_REQUIRED")
    long optionId;
    @NotBlank(message = "OPTION_VALUE_INVALID")
    String value;
}
