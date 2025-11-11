package com.nguyenkhang.mobile_store.mapper;

import com.nguyenkhang.mobile_store.dto.response.purchase_order.PurchaseOrderResponse;
import com.nguyenkhang.mobile_store.dto.response.SimplePurchaseOrderResponse;
import com.nguyenkhang.mobile_store.entity.PurchaseOrder;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PurchaseOrderMapper {
    PurchaseOrderResponse toPurchaseOrderResponse(PurchaseOrder purchaseOrder);
    SimplePurchaseOrderResponse toSimplePurchaseOrderResponse(PurchaseOrder purchaseOrder);
}
