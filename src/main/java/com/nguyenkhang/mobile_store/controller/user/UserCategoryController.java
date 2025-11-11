package com.nguyenkhang.mobile_store.controller.user;

import com.nguyenkhang.mobile_store.dto.ApiResponse;
import com.nguyenkhang.mobile_store.dto.request.CategoryCreationRequest;
import com.nguyenkhang.mobile_store.dto.request.CategoryUpdateRequest;
import com.nguyenkhang.mobile_store.dto.response.category.CategoryResponse;
import com.nguyenkhang.mobile_store.dto.response.category.CategoryResponseForCustomer;
import com.nguyenkhang.mobile_store.service.CategoryService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user/categories")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserCategoryController {

    CategoryService categoryService;



    @GetMapping
    public ApiResponse<List<CategoryResponseForCustomer>> getCategories(){
        return ApiResponse.<List<CategoryResponseForCustomer>>builder()
                .result(categoryService.getCategoriesForUser())
                .build();
    }

}
