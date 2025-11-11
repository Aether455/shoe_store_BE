package com.nguyenkhang.mobile_store.mapper;

import com.nguyenkhang.mobile_store.dto.response.IntrospectResponse;
import com.nguyenkhang.mobile_store.dto.response.InventoryResponse;
import com.nguyenkhang.mobile_store.entity.Inventory;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface InventoryMapper {
    InventoryResponse toInventoryResponse(Inventory inventory);
}
