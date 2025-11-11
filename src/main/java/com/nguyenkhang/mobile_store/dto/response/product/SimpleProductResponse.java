package com.nguyenkhang.mobile_store.dto.response.product;

import com.nguyenkhang.mobile_store.dto.response.brand.BrandResponse;
import com.nguyenkhang.mobile_store.dto.response.brand.SimpleBrandResponse;
import com.nguyenkhang.mobile_store.dto.response.category.SimpleCategoryResponse;
import com.nguyenkhang.mobile_store.dto.response.product_variant.SimpleProductVariantResponse;
import com.nguyenkhang.mobile_store.dto.response.user.SimpleUserResponse;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SimpleProductResponse {

    Long id;

    String name;
    String mainImageUrl;

    SimpleCategoryResponse category;


    SimpleBrandResponse brand;

    SimpleUserResponse createBy;

    SimpleUserResponse updateBy;

    LocalDateTime createAt;

    LocalDateTime updateAt;

}

