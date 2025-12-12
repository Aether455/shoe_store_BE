package com.nguyenkhang.shoe_store.controller;

import jakarta.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import com.nguyenkhang.shoe_store.dto.ApiResponseDTO;
import com.nguyenkhang.shoe_store.dto.request.customer.CustomerCreationRequest;
import com.nguyenkhang.shoe_store.dto.request.customer.CustomerUpdateRequest;
import com.nguyenkhang.shoe_store.dto.response.customer.CustomerResponse;
import com.nguyenkhang.shoe_store.dto.response.customer.CustomerResponseForUser;
import com.nguyenkhang.shoe_store.service.CustomerService;

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
    public ApiResponseDTO<CustomerResponse> create(@RequestBody @Valid CustomerCreationRequest request) {
        return ApiResponseDTO.<CustomerResponse>builder()
                .message("Success!")
                .result(customerService.create(request))
                .build();
    }

    @GetMapping
    public ApiResponseDTO<Page<CustomerResponse>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy) {
        return ApiResponseDTO.<Page<CustomerResponse>>builder()
                .result(customerService.getALl(page, size, sortBy))
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponseDTO<CustomerResponse> getById(@PathVariable long id) {
        return ApiResponseDTO.<CustomerResponse>builder()
                .result(customerService.getById(id))
                .build();
    }

    @PutMapping("/{id}")
    public ApiResponseDTO<CustomerResponse> update(
            @PathVariable long id, @RequestBody @Valid CustomerUpdateRequest request) {
        return ApiResponseDTO.<CustomerResponse>builder()
                .result(customerService.update(id, request))
                .build();
    }

    @PutMapping("/current_user") // dành cho customer
    public ApiResponseDTO<CustomerResponseForUser> updateByCurrentUser(
            @RequestBody @Valid CustomerUpdateRequest request) {
        return ApiResponseDTO.<CustomerResponseForUser>builder()
                .result(customerService.updateByCurrentUser(request))
                .build();
    }

    @DeleteMapping("/{id}")
    public ApiResponseDTO<String> delete(@PathVariable long id) {
        customerService.delete(id);
        return ApiResponseDTO.<String>builder()
                .result("Customer has been deleted")
                .build();
    }

    @GetMapping("/search")
    public ApiResponseDTO<Page<CustomerResponse>> searchCustomers(
            @RequestParam(defaultValue = "0") int page, @RequestParam String keyword) {
        return ApiResponseDTO.<Page<CustomerResponse>>builder()
                .result(customerService.searchCustomers(keyword, page))
                .build();
    }

    @GetMapping("/me") // lấy thông tin dành cho customer
    public ApiResponseDTO<CustomerResponseForUser> getCustomerByCurrentUser() {
        return ApiResponseDTO.<CustomerResponseForUser>builder()
                .result(customerService.getCustomerByCurrentUser())
                .build();
    }
}
