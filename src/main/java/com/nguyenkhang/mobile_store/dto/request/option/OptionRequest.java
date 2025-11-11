package com.nguyenkhang.mobile_store.dto.request.option;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OptionRequest {
    @NotBlank(message = "OPTION_NAME_INVALID")
    String name;
}
