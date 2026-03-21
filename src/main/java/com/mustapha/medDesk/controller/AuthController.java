package com.mustapha.medDesk.controller;


import com.mustapha.medDesk.dto.request.auth.register.RegisterDtoRequest;
import com.mustapha.medDesk.dto.request.auth.signup.LoginDtoRequest;
import com.mustapha.medDesk.dto.request.refreshToken.RefreshTokenDtoRequest;
import com.mustapha.medDesk.dto.response.ApiResponse;

import com.mustapha.medDesk.dto.response.auth.AuthDtoResponse;
import com.mustapha.medDesk.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<AuthDtoResponse>> register(
            @Valid @RequestBody RegisterDtoRequest request,
            HttpServletRequest http
    ) {
        AuthDtoResponse data = authService.register(request);
        ApiResponse<AuthDtoResponse> response = ApiResponse.success("Registration successful", data);
        response.setPath(http.getRequestURI());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthDtoResponse>> login(
            @Valid @RequestBody LoginDtoRequest request,
            HttpServletRequest http
    ) {
        AuthDtoResponse data = authService.Login(request);
        ApiResponse<AuthDtoResponse> response = ApiResponse.success("Login successful", data);
        response.setPath(http.getRequestURI());
        return ResponseEntity.ok(response);
    }


    @PostMapping("/refresh")
    public ResponseEntity<ApiResponse<AuthDtoResponse>> refresh(
            @RequestBody RefreshTokenDtoRequest request,
            HttpServletRequest http
    ) {
        AuthDtoResponse data = authService.refreshToken(request.getRefreshToken());

        ApiResponse<AuthDtoResponse> response =
                ApiResponse.success("Token refreshed successfully", data);

        response.setPath(http.getRequestURI());

        return ResponseEntity.ok(response);
    }

}