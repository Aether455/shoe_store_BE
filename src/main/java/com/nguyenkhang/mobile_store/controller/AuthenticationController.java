package com.nguyenkhang.mobile_store.controller;

import java.text.ParseException;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nguyenkhang.mobile_store.annotations.ApiCommonResponses;
import com.nguyenkhang.mobile_store.dto.ApiResponseDTO;
import com.nguyenkhang.mobile_store.dto.request.AuthenticationRequest;
import com.nguyenkhang.mobile_store.dto.request.LogoutRequest;
import com.nguyenkhang.mobile_store.dto.request.RefreshRequest;
import com.nguyenkhang.mobile_store.dto.response.AuthenticationResponse;
import com.nguyenkhang.mobile_store.service.AuthenticationService;
import com.nimbusds.jose.JOSEException;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping("/auth")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Tag(name = "Authentication Controller", description = "Đăng nhập, refresh và Logout")
public class AuthenticationController {
    AuthenticationService authenticationService;

    @ApiCommonResponses
    @Operation(summary = "Đăng nhập")
    @PostMapping("/login")
    public ApiResponseDTO<AuthenticationResponse> authenticate(@RequestBody @Valid AuthenticationRequest request) {

        AuthenticationResponse result = authenticationService.authenticate(request);
        return ApiResponseDTO.<AuthenticationResponse>builder().result(result).build();
    }

    @ApiCommonResponses
    @Operation(summary = "Refresh")
    @PostMapping("/refresh")
    public ApiResponseDTO<AuthenticationResponse> refresh(@RequestBody @Valid RefreshRequest request)
            throws ParseException, JOSEException {

        AuthenticationResponse result = authenticationService.refreshToken(request);
        return ApiResponseDTO.<AuthenticationResponse>builder().result(result).build();
    }

    @ApiCommonResponses
    @Operation(summary = "Đăng xuất")
    @PostMapping("/logout")
    public ApiResponseDTO<Void> logout(@RequestBody @Valid LogoutRequest request) throws ParseException, JOSEException {
        authenticationService.logout(request);
        return ApiResponseDTO.<Void>builder().message("Logout successfully").build();
    }
}
