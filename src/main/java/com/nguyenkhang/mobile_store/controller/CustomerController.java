package com.nguyenkhang.mobile_store.controller;

import jakarta.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import com.nguyenkhang.mobile_store.dto.ApiResponse;
import com.nguyenkhang.mobile_store.dto.request.CustomerCreationRequest;
import com.nguyenkhang.mobile_store.dto.request.CustomerUpdateRequest;
import com.nguyenkhang.mobile_store.dto.response.customer.CustomerResponse;
import com.nguyenkhang.mobile_store.dto.response.customer.CustomerResponseForUser;
import com.nguyenkhang.mobile_store.service.CustomerService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping("/customers")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CustomerController {
    CustomerService customerService;

    @PostMapping
    public ApiResponse<CustomerResponse> create(@RequestBody @Valid CustomerCreationRequest request) {
        return ApiResponse.<CustomerResponse>builder()
                .message("Success!")
                .result(customerService.create(request))
                .build();
    }

    @GetMapping
    public ApiResponse<Page<CustomerResponse>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy) {
        return ApiResponse.<Page<CustomerResponse>>builder()
                .result(customerService.getALl(page, size, sortBy))
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<CustomerResponse> getById(@PathVariable long id) {
        return ApiResponse.<CustomerResponse>builder()
                .message("Success!")
                .result(customerService.getById(id))
                .build();
    }

    @PutMapping("/{id}")
    public ApiResponse<CustomerResponse> update(
            @PathVariable long id, @RequestBody @Valid CustomerUpdateRequest request) {
        return ApiResponse.<CustomerResponse>builder()
                .message("Success!")
                .result(customerService.update(id, request))
                .build();
    }

    @PutMapping("/current_user")//bỏ qua
    public ApiResponse<CustomerResponseForUser> updateByCurrentUser(@RequestBody @Valid CustomerUpdateRequest request) {
        return ApiResponse.<CustomerResponseForUser>builder()
                .message("Success!")
                .result(customerService.updateByCurrentUser(request))
                .build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<String> delete(@PathVariable long id) {
        customerService.delete(id);
        return ApiResponse.<String>builder()
                .message("Success!")
                .result("Size has been deleted")
                .build();
    }

    @GetMapping("/search")
    public ApiResponse<Page<CustomerResponse>> searchCustomers(
            @RequestParam(defaultValue = "0") int page, @RequestParam String keyword) {
        return ApiResponse.<Page<CustomerResponse>>builder()
                .result(customerService.searchCustomers(keyword, page))
                .build();
    }

    @GetMapping("/me")//bỏ qua
    public ApiResponse<CustomerResponseForUser> getCustomerByCurrentUser() {
        return ApiResponse.<CustomerResponseForUser>builder()
                .result(customerService.getCustomerByCurrentUser())
                .build();
    }
}
