package com.nguyenkhang.mobile_store.mapper;

import com.nguyenkhang.mobile_store.dto.response.option.SimpleOptionResponse;
import org.mapstruct.Mapper;

import com.nguyenkhang.mobile_store.dto.request.option.OptionRequest;
import com.nguyenkhang.mobile_store.dto.response.option.OptionResponse;
import com.nguyenkhang.mobile_store.entity.Option;

@Mapper(componentModel = "spring")
public interface OptionMapper {
    Option toOption(OptionRequest request);

    OptionResponse toOptionResponse(Option option);

    SimpleOptionResponse toSimpleOptionResponse(Option option);

}
