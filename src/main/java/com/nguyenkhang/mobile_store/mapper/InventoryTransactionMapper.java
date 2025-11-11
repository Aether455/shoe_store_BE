package com.nguyenkhang.mobile_store.mapper;

import com.nguyenkhang.mobile_store.dto.response.InventoryTransactionResponse;
import com.nguyenkhang.mobile_store.entity.Inventory;
import com.nguyenkhang.mobile_store.entity.InventoryTransaction;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface InventoryTransactionMapper {
    InventoryTransactionResponse toInventoryTransactionResponse(InventoryTransaction inventoryTransaction);
}
