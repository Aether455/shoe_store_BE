    package com.nguyenkhang.mobile_store.dto.request;

    import com.nguyenkhang.mobile_store.entity.ProductVariant;
    import com.nguyenkhang.mobile_store.entity.PurchaseOrder;
    import jakarta.persistence.*;
    import lombok.*;
    import lombok.experimental.FieldDefaults;

    import java.math.BigDecimal;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public class PurchaseOrderItemRequest {

        Long productVariantId;

        int quantity;

        BigDecimal pricePerUnit;

        BigDecimal total;
    }
