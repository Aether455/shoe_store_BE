package com.nguyenkhang.mobile_store.mapper;

import com.nguyenkhang.mobile_store.dto.request.SupplierRequest;
import com.nguyenkhang.mobile_store.dto.response.SupplierResponse;
import com.nguyenkhang.mobile_store.entity.Supplier;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface SupplierMapper {
    Supplier toSupplier(SupplierRequest request);

    SupplierResponse toSupplierResponse(Supplier supplier);

    void update(@MappingTarget Supplier supplier, SupplierRequest request);
}
