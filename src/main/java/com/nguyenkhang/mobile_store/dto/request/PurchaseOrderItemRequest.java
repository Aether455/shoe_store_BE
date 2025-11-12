    package com.nguyenkhang.mobile_store.dto.request;

    import com.nguyenkhang.mobile_store.entity.ProductVariant;
    import com.nguyenkhang.mobile_store.entity.PurchaseOrder;
    import jakarta.persistence.*;
    import jakarta.validation.constraints.NotNull;
    import jakarta.validation.constraints.Positive;
    import jakarta.validation.constraints.PositiveOrZero;
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

        @NotNull(message = "PRODUCT_VARIANT_REQUIRED")
        Long productVariantId;

        @PositiveOrZero(message = "QUANTITY_INVALID")
        @NotNull(message = "QUANTITY_REQUIRED")
        int quantity;

        @Positive(message = "PRICE_INVALID")
        @NotNull(message = "PRICE_REQUIRED")
        BigDecimal pricePerUnit;

        @Positive(message = "TOTAL_PRICE_INVALID")
        @NotNull(message = "TOTAL_PRICE_REQUIRED")
        BigDecimal total;
    }
