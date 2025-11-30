package com.nguyenkhang.mobile_store.service;

import java.util.List;

import com.nguyenkhang.mobile_store.exception.AppException;
import com.nguyenkhang.mobile_store.exception.ErrorCode;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CategoryService {
    CategoryRepository categoryRepository;
    CategoryMapper categoryMapper;

    @Transactional
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_USER')")
    public CategoryResponse create(CategoryCreationRequest request) {
        Category category = categoryMapper.toCategory(request);

        try {
            categoryRepository.save(category);
        } catch (DataIntegrityViolationException e) {
            throw new AppException(ErrorCode.CATEGORY_EXISTED);
        }
        return categoryMapper.toCategoryResponse(category);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_USER')")
    public List<CategoryResponse> getCategories() {
        return categoryRepository.findAll().stream()
                .map(categoryMapper::toCategoryResponse)
                .toList();
    }

    public List<CategoryResponseForCustomer> getCategoriesForUser() {
        return categoryRepository.findAll().stream()
                .map(categoryMapper::toCategoryResponseForCustomer)
                .toList();
    }

    @Transactional
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_USER')")
    public CategoryResponse update(long id, CategoryUpdateRequest request) {
        Category category =
                categoryRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_EXISTED));

        categoryMapper.updateCategory(category, request);

        return categoryMapper.toCategoryResponse(categoryRepository.save(category));
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public void delete(long id) {
        categoryRepository.deleteById(id);
    }
}
