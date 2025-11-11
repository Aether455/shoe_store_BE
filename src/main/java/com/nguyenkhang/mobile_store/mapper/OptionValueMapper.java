package com.nguyenkhang.mobile_store.mapper;

import com.nguyenkhang.mobile_store.dto.request.option.OptionValueRequest;
import com.nguyenkhang.mobile_store.dto.response.option.OptionResponse;
import com.nguyenkhang.mobile_store.dto.response.option.OptionValueResponse;
import com.nguyenkhang.mobile_store.entity.OptionValue;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OptionValueMapper {
    @Mapping(target = "option", ignore = true)
    OptionValue toOptionValue(OptionValueRequest request);

    OptionValueResponse toOptionValueResponse(OptionValue optionValue);
}
