package com.nguyenkhang.mobile_store.controller;

import com.nguyenkhang.mobile_store.dto.ApiResponse;
import com.nguyenkhang.mobile_store.dto.request.CategoryCreationRequest;
import com.nguyenkhang.mobile_store.dto.request.CategoryUpdateRequest;
import com.nguyenkhang.mobile_store.dto.response.category.CategoryResponse;
import com.nguyenkhang.mobile_store.service.CategoryService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CategoryController {

    CategoryService categoryService;

    @PostMapping
    public ApiResponse<CategoryResponse> create(@RequestBody CategoryCreationRequest request){
        var result = categoryService.create(request);

        return ApiResponse.<CategoryResponse>builder()
                .result(result)
                .build();
    }

    @GetMapping
    public ApiResponse<List<CategoryResponse>> getCategories(){
        return ApiResponse.<List<CategoryResponse>>builder()
                .result(categoryService.getCategories())
                .build();
    }

    @PutMapping("/{categoryId}")
    public ApiResponse<CategoryResponse> update(@PathVariable long categoryId, @RequestBody CategoryUpdateRequest request){
        var category = categoryService.update(categoryId,request);
        return ApiResponse.<CategoryResponse>builder()
                .result(category)
                .build();

    }

    @DeleteMapping("/{categoryId}")
    public ApiResponse<String> delete(@PathVariable long categoryId){
        categoryService.delete(categoryId);
        return ApiResponse.<String>builder()
                .message("Success!").result("Category has been deleted!")
                .build();
    }
}
