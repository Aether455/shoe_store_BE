package com.nguyenkhang.mobile_store.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder // tao mot builder pattern
@FieldDefaults(
        level =
                AccessLevel
                        .PRIVATE)
public class AuthenticationRequest {
    String username;
    String password;
}
