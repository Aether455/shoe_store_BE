package com.nguyenkhang.mobile_store.controller;

import com.nguyenkhang.mobile_store.dto.ApiResponse;
import com.nguyenkhang.mobile_store.dto.request.AuthenticationRequest;
import com.nguyenkhang.mobile_store.dto.request.LogoutRequest;
import com.nguyenkhang.mobile_store.dto.request.RefreshRequest;
import com.nguyenkhang.mobile_store.dto.response.AuthenticationResponse;
import com.nguyenkhang.mobile_store.service.AuthenticationService;
import com.nimbusds.jose.JOSEException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

@RestController
@RequestMapping("/auth")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class AuthenticationController {
    AuthenticationService authenticationService;

    @PostMapping("/login")
    public ApiResponse<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {

        AuthenticationResponse result = authenticationService.authenticate(request);
        return ApiResponse.<AuthenticationResponse>builder().result(result).build();
    }

    @PostMapping("/refresh")
    public ApiResponse<AuthenticationResponse> refresh(@RequestBody RefreshRequest request) throws ParseException, JOSEException {

        AuthenticationResponse result = authenticationService.refreshToken(request);
        return ApiResponse.<AuthenticationResponse>builder().result(result).build();
    }

    @PostMapping("/logout")
    public ApiResponse<Void> logout(@RequestBody LogoutRequest request) throws ParseException, JOSEException {
        authenticationService.logout(request);
        return ApiResponse.<Void>builder().message("Logout successfully").build();
    }
}
