package com.nguyenkhang.mobile_store.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UploadFileCloudinaryResponse {
    String publicUrl;
    String publicId;
}
