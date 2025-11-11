package com.nguyenkhang.mobile_store.mapper;

import com.nguyenkhang.mobile_store.dto.request.option.OptionRequest;
import com.nguyenkhang.mobile_store.dto.response.option.OptionResponse;
import com.nguyenkhang.mobile_store.entity.Option;
import com.nguyenkhang.mobile_store.entity.OptionValue;
import jakarta.persistence.MapKey;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OptionMapper {
    Option toOption(OptionRequest request);

    OptionResponse toOptionResponse(Option option);

}
