package com.nguyenkhang.mobile_store.utils;

import com.nguyenkhang.mobile_store.entity.OptionValue;
import com.nguyenkhang.mobile_store.exception.AppException;
import com.nguyenkhang.mobile_store.exception.ErrorCode;

import java.util.Set;
import java.util.stream.Collectors;

public class VariantUtils {
    public static String createVariantSignature(Set<OptionValue> optionValues){
        if (optionValues==null || optionValues.isEmpty() ){
            throw new AppException(ErrorCode.OPTION_VALUE_EMPTY);
        }

        return optionValues.stream().map(
                        (value)->value.getId().toString()
                ).sorted()
                .collect(Collectors.joining("_"));
    }
}
