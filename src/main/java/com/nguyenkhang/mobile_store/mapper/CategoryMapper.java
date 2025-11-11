package com.nguyenkhang.mobile_store.mapper;

import com.nguyenkhang.mobile_store.dto.request.CategoryCreationRequest;
import com.nguyenkhang.mobile_store.dto.request.CategoryUpdateRequest;
import com.nguyenkhang.mobile_store.dto.response.category.CategoryResponse;
import com.nguyenkhang.mobile_store.dto.response.category.CategoryResponseForCustomer;
import com.nguyenkhang.mobile_store.entity.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    @Mapping(target = "createAt",ignore = true)
    @Mapping(target = "updateAt",ignore = true)
    Category toCategory(CategoryCreationRequest request);


    CategoryResponse toCategoryResponse(Category category);
    CategoryResponseForCustomer toCategoryResponseForCustomer(Category category);

    @Mapping(target = "createAt",ignore = true)
    @Mapping(target = "updateAt",ignore = true)
    @Mapping(target = "createBy",ignore = true)
    @Mapping(target = "updateBy",ignore = true)
    void updateCategory(@MappingTarget Category category, CategoryUpdateRequest request);

}
