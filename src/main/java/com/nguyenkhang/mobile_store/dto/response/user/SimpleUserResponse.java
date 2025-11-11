package com.nguyenkhang.mobile_store.dto.response.user;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SimpleUserResponse {
    String id;
    String username;
    String email;
}
