package com.nguyenkhang.mobile_store.mapper;

import com.nguyenkhang.mobile_store.dto.request.BrandRequest;
import com.nguyenkhang.mobile_store.dto.response.brand.BrandResponse;
import com.nguyenkhang.mobile_store.dto.response.brand.BrandResponseForCustomer;
import com.nguyenkhang.mobile_store.entity.Brand;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface BrandMapper {

    @Mapping(target = "createAt",ignore = true)
    @Mapping(target = "updateAt",ignore = true)
    Brand toBrand(BrandRequest request);
    BrandResponse toBrandResponse(Brand brand);

    BrandResponseForCustomer toBrandResponseForCustomer(Brand brand);

    @Mapping(target = "createAt",ignore = true)
    @Mapping(target = "updateAt",ignore = true)
    @Mapping(target = "createBy",ignore = true)
    @Mapping(target = "updateBy",ignore = true)
    void updateBrand(@MappingTarget Brand brand, BrandRequest request);
}
