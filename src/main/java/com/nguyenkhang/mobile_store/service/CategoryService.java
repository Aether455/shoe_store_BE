package com.nguyenkhang.mobile_store.service;

import com.nguyenkhang.mobile_store.dto.request.CategoryCreationRequest;
import com.nguyenkhang.mobile_store.dto.request.CategoryUpdateRequest;
import com.nguyenkhang.mobile_store.dto.response.category.CategoryResponse;
import com.nguyenkhang.mobile_store.dto.response.category.CategoryResponseForCustomer;
import com.nguyenkhang.mobile_store.entity.Category;
import com.nguyenkhang.mobile_store.mapper.CategoryMapper;
import com.nguyenkhang.mobile_store.repository.CategoryRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CategoryService {
    CategoryRepository categoryRepository;
    CategoryMapper categoryMapper;

    @Transactional
    public CategoryResponse create(CategoryCreationRequest request) {
        Category category = categoryMapper.toCategory(request);

        categoryRepository.save(category);
        return categoryMapper.toCategoryResponse(category);
    }

    public List<CategoryResponse> getCategories() {
        return categoryRepository.findAll().stream().map(categoryMapper::toCategoryResponse).toList();
    }

    public List<CategoryResponseForCustomer> getCategoriesForUser() {
        return categoryRepository.findAll().stream().map(categoryMapper::toCategoryResponseForCustomer).toList();
    }

    @Transactional
    public CategoryResponse update(long id, CategoryUpdateRequest request) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new RuntimeException("Category not existed!"));

        categoryMapper.updateCategory(category, request);


        return categoryMapper.toCategoryResponse(categoryRepository.save(category));
    }

    public void delete(long id) {
        categoryRepository.deleteById(id);
    }
}
