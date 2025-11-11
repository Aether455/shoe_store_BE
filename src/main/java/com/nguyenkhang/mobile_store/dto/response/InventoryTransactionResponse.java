package com.nguyenkhang.mobile_store.dto.response;

import com.nguyenkhang.mobile_store.dto.response.product_variant.SimpleProductVariantResponse;
import com.nguyenkhang.mobile_store.dto.response.user.SimpleUserResponse;
import com.nguyenkhang.mobile_store.enums.InventoryReferenceType;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class InventoryTransactionResponse {

    Long id;


    SimpleWarehouseResponse warehouse;

    SimpleProductVariantResponse productVariant;

    InventoryReferenceType type;

    int quantityChange;

    Long referenceId;

    String note;


    SimpleUserResponse createBy;

    LocalDateTime createAt;


}
