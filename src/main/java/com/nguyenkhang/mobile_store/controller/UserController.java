package com.nguyenkhang.mobile_store.controller;

import java.util.List;

import com.nguyenkhang.mobile_store.dto.request.user.*;
import com.nguyenkhang.mobile_store.dto.response.user.*;
import jakarta.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import com.nguyenkhang.mobile_store.dto.ApiResponse;
import com.nguyenkhang.mobile_store.service.UserService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping("/users")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class UserController {
    UserService userService;

    @PostMapping
    public ApiResponse<UserResponse> createUser(@RequestBody @Valid UserCreationRequest request) {

        return ApiResponse.<UserResponse>builder()
                .result(userService.createUser(request))
                .build();
    }

    @PostMapping("/customers")/// dành cho khách hàng
    public ApiResponse<UserResponseForCustomer> createUserForCustomer(
            @RequestBody @Valid UserCreationRequestForCustomer request) {

        return ApiResponse.<UserResponseForCustomer>builder()
                .result(userService.createUserForCustomer(request))
                .build();
    }

    @PostMapping("/staffs") //dành cho trang quản lý nhân viên
    public ApiResponse<UserResponse> createUserForStaff(
            @RequestBody @Valid UserCreationRequestForStaff request) {

        return ApiResponse.<UserResponse>builder()
                .result(userService.createUserForStaff(request))
                .build();
    }

    @GetMapping
    public ApiResponse<Page<UserResponse>> getUsers( @RequestParam(defaultValue = "0") int page,
                                                     @RequestParam(defaultValue = "10") int size,
                                                     @RequestParam(defaultValue = "id") String sortBy) {
        return ApiResponse.<Page<UserResponse>>builder()
                .result(userService.getUsers(page, size, sortBy))
                .build();
    }

    @GetMapping("/{userId}")//detail
    public ApiResponse<UserResponse> getUserById(@PathVariable("userId") long userId) {
        return ApiResponse.<UserResponse>builder()
                .result(userService.getUserById(userId))
                .build();
    }

    @PutMapping("/{userId}")
    public ApiResponse<UserResponse> updateUser(
            @PathVariable("userId") long userId, @RequestBody @Valid UserUpdateRequest request) {

        return ApiResponse.<UserResponse>builder()
                .result(userService.updateUser(userId, request))
                .build();
    }

    @DeleteMapping("/{userId}")
    ApiResponse<String> deleteUser(@PathVariable long userId) {
        userService.deleteUser(userId);
        return ApiResponse.<String>builder()
                .result("User has been deleted")
                .build();
    }

    @PutMapping("/change-password")
    ApiResponse<SimpleUserResponse> changePassword(@RequestBody UserChangePasswordRequest request){
        return ApiResponse.<SimpleUserResponse>builder()
                .result(userService.changePassword(request))
                .build();
    }

    @GetMapping("/search")
    public ApiResponse<Page<UserResponse>> searchUsers(
            @RequestParam(defaultValue = "0") int page, @RequestParam String keyword) {
        return ApiResponse.<Page<UserResponse>>builder()
                .result(userService.searchUser(keyword, page))
                .build();
    }

    @GetMapping("/me")
    public ApiResponse<SimpleUserInfoResponse> getMyInfo() {
        return ApiResponse.<SimpleUserInfoResponse>builder()
                .result(userService.getMyInfo())
                .build();
    }

}
