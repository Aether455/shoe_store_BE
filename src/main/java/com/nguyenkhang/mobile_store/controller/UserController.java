package com.nguyenkhang.mobile_store.controller;

import com.nguyenkhang.mobile_store.dto.ApiResponse;
import com.nguyenkhang.mobile_store.dto.request.user.UserCreationRequest;
import com.nguyenkhang.mobile_store.dto.request.user.UserCreationRequestForCustomer;
import com.nguyenkhang.mobile_store.dto.request.user.UserCreationRequestForStaff;
import com.nguyenkhang.mobile_store.dto.request.user.UserUpdateRequest;
import com.nguyenkhang.mobile_store.dto.response.SupplierResponse;
import com.nguyenkhang.mobile_store.dto.response.user.UserResponse;
import com.nguyenkhang.mobile_store.dto.response.user.UserResponseForCustomer;
import com.nguyenkhang.mobile_store.dto.response.user.UserResponseForStaff;
import com.nguyenkhang.mobile_store.service.UserService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @PostMapping("/customers")
    public ApiResponse<UserResponseForCustomer> createUserForCustomer(@RequestBody @Valid UserCreationRequestForCustomer request) {

        return ApiResponse.<UserResponseForCustomer>builder()
                .result(userService.createUserForCustomer(request))
                .build();
    }
    @PostMapping("/staffs")
    public ApiResponse<UserResponseForStaff> createUserForStaff(@RequestBody @Valid UserCreationRequestForStaff request) {

        return ApiResponse.<UserResponseForStaff>builder()
                .result(userService.createUserForStaff(request))
                .build();
    }

    @GetMapping
    public ApiResponse<List<UserResponse>> getUsers() {
        return ApiResponse.<List<UserResponse>>builder().result(userService.getUsers()).build();
    }

    @GetMapping("/{userId}")
    public ApiResponse<UserResponse> getUser(@PathVariable("userId") long userId) {
        return ApiResponse.<UserResponse>builder().result(userService.getUserById(userId)).build();
    }

    @PutMapping("/{userId}")
    public ApiResponse<UserResponse> updateUser(@PathVariable("userId") long userId, @RequestBody @Valid UserUpdateRequest request) {

        return ApiResponse.<UserResponse>builder()
                .result(userService.updateUser(userId, request))
                .build();
    }

    @DeleteMapping("/{userId}")
    ApiResponse<String> deleteUser(@PathVariable long userId) {
        userService.deleteUser(userId);
        return ApiResponse.<String>builder().message("Success!").result("User has been deleted").build();
    }

    @GetMapping("/search")
    public ApiResponse<Page<UserResponse>> searchSupplier(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam String keyword){
        return ApiResponse.<Page<UserResponse>>builder().result(userService.searchUser(keyword, page)).build();
    }
}
