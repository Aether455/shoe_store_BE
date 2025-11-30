package com.nguyenkhang.mobile_store.dto.request.products;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class ProductSearchCriteria {

    String productName;
    Long categoryId;
    Long brandId;

    String sku;
    Double minPrice;
    Double maxPrice;
    Integer minQuantity;

    LocalDateTime createAtStart;
    LocalDateTime createAtEnd;
}
