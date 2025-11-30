package com.nguyenkhang.mobile_store.controller;

import com.nguyenkhang.mobile_store.dto.request.StaffUpdateRequest;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import com.nguyenkhang.mobile_store.dto.ApiResponse;
import com.nguyenkhang.mobile_store.dto.request.StaffRequest;
import com.nguyenkhang.mobile_store.dto.response.StaffResponse;
import com.nguyenkhang.mobile_store.service.StaffService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping("/staffs")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class StaffController {
    StaffService staffService;

    @PostMapping //bỏ qua
    public ApiResponse<StaffResponse> create(@RequestBody StaffRequest request) {
        var response = staffService.create(request);
        return ApiResponse.<StaffResponse>builder()
                .result(response)
                .build();
    }

    @GetMapping
    public ApiResponse<Page<StaffResponse>> getStaffs(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy) {
        var response = staffService.getStaffs(page, size, sortBy);
        return ApiResponse.<Page<StaffResponse>>builder()
                .result(response)
                .build();
    }

    @GetMapping("/{staffId}")
    public ApiResponse<StaffResponse> getById(@PathVariable long staffId) {
        var response = staffService.getStaffById(staffId);
        return ApiResponse.<StaffResponse>builder()
                .result(response)
                .build();
    }

    @PutMapping("/{staffId}")
    public ApiResponse<StaffResponse> update(@PathVariable long staffId, @RequestBody StaffUpdateRequest request) {
        var response = staffService.update(staffId, request);
        return ApiResponse.<StaffResponse>builder()
                .result(response)
                .build();
    }

    @DeleteMapping("/{staffId}")
    public ApiResponse<String> delete(@PathVariable long staffId) {
        staffService.delete(staffId);
        return ApiResponse.<String>builder()
                .result("Staff has been deleted")
                .build();
    }

    @GetMapping("/search")
    public ApiResponse<Page<StaffResponse>> searchStaffs(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam String keyword) {
        return ApiResponse.<Page<StaffResponse>>builder()
                .result(staffService.searchStaffs(keyword, page,size))
                .build();
    }
}
