package com.nguyenkhang.mobile_store.controller;

import com.nguyenkhang.mobile_store.dto.ApiResponse;
import com.nguyenkhang.mobile_store.dto.request.option.OptionRequest;
import com.nguyenkhang.mobile_store.dto.response.option.OptionResponse;
import com.nguyenkhang.mobile_store.service.OptionService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/options")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OptionController {
    OptionService optionService;

    @GetMapping
    public ApiResponse<List<OptionResponse>> getAll(){
        return ApiResponse.<List<OptionResponse>>builder().result(optionService.getAll()).build();
    }

    @PostMapping
    public ApiResponse<OptionResponse> create(@RequestBody OptionRequest request){
        return ApiResponse.<OptionResponse>builder().result(optionService.create(request)).build();
    }
    @GetMapping("/{id}")
    public ApiResponse<OptionResponse> getById(@PathVariable long id){
        return ApiResponse.<OptionResponse>builder().result(optionService.getById(id)).build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<String> delete(@PathVariable long id){
        optionService.delete(id);
        return ApiResponse.<String>builder().result("Option has been deleted").build();
    }
}
