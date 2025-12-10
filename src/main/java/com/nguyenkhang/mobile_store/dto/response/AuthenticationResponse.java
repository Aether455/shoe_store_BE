package com.nguyenkhang.mobile_store.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder // tao mot builder pattern
@FieldDefaults(
        level =
                AccessLevel
                        .PRIVATE) // tat ca field trong class se mac dinh la private va khong can dung private truoc cac
// field nua
public class AuthenticationResponse {
    @Schema(
            description = "Chuỗi JWT Access Token dùng để xác thực các request sau",
            example = "eyJhbGciOiJIUzI1NiJ9...")
    String token;

    @Schema(description = "Trạng thái xác thực (true: thành công, false: thất bại)", example = "true")
    boolean authenticated;
}
