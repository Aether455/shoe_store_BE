package com.nguyenkhang.mobile_store.dto.response;

import com.nguyenkhang.mobile_store.dto.response.product_variant.SimpleProductVariantResponse;
import com.nguyenkhang.mobile_store.dto.response.user.SimpleUserResponse;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)

public class InventoryResponse {

    Long id;


    WarehouseResponse warehouse;


    SimpleProductVariantResponse productVariant;


    int quantity;

    SimpleUserResponse createBy;

    SimpleUserResponse updateBy;

    LocalDateTime createAt;
    LocalDateTime updateAt;
}
