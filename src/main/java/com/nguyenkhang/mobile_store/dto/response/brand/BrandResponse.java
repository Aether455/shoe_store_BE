package com.nguyenkhang.mobile_store.dto.response.brand;

import java.time.LocalDate;

import com.nguyenkhang.mobile_store.dto.response.user.SimpleUserResponse;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BrandResponse {

    @Schema(description = "ID định danh duy nhất của thương hiệu", example = "brand_001")
    String id;

    @Schema(description = "Tên thương hiệu", example = "Gucci")
    String name;

    @Schema(
            description = "Mô tả chi tiết về thương hiệu",
            example = "Tập đoàn công nghệ đa quốc gia của Mỹ, chuyên thiết kế và bán thiết bị điện tử tiêu dùng.")
    String description;

    @Schema(description = "Thông tin tóm tắt của nhân viên tạo bản ghi")
    SimpleUserResponse createBy;

    @Schema(description = "Thông tin tóm tắt của nhân viên cập nhật lần cuối")
    SimpleUserResponse updateBy;

    @Schema(description = "Ngày tạo bản ghi (Format: yyyy-MM-dd)", example = "2023-10-15")
    LocalDate createAt;

    @Schema(description = "Ngày cập nhật gần nhất (Format: yyyy-MM-dd)", example = "2024-01-20")
    LocalDate updateAt;
}
