package com.nguyenkhang.mobile_store.controller;

import com.nguyenkhang.mobile_store.dto.ApiResponse;
import com.nguyenkhang.mobile_store.dto.request.option.OptionValueRequest;
import com.nguyenkhang.mobile_store.dto.response.option.OptionValueResponse;
import com.nguyenkhang.mobile_store.service.OptionValueService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/option_values")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OptionValueController {
    OptionValueService optionValueService;

    @GetMapping("/{optionId}")
    public ApiResponse<List<OptionValueResponse>> getByOptionId(@PathVariable long optionId){
        return ApiResponse.<List<OptionValueResponse>>builder().result(optionValueService.getByOptionId(optionId)).build();
    }

    @PostMapping
    public ApiResponse<OptionValueResponse> create(@RequestBody @Valid OptionValueRequest request){
        return ApiResponse.<OptionValueResponse>builder().result(optionValueService.create(request)).build();
    }
}
