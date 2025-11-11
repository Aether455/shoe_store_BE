package com.nguyenkhang.mobile_store.mapper;

import com.nguyenkhang.mobile_store.dto.request.WarehouseRequest;
import com.nguyenkhang.mobile_store.dto.response.WarehouseResponse;
import com.nguyenkhang.mobile_store.entity.Warehouse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface WarehouseMapper {
    Warehouse toWarehouse(WarehouseRequest request);
    WarehouseResponse toWarehouseResponse(Warehouse warehouse);
    void update(@MappingTarget Warehouse warehouse, WarehouseRequest request);
}
