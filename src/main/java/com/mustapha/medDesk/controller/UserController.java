package com.mustapha.medDesk.controller;

import com.mustapha.medDesk.dto.request.user.UserDtoRequest;
import com.mustapha.medDesk.dto.request.user.UserUpdateRequest;
import com.mustapha.medDesk.dto.response.ApiResponse;
import com.mustapha.medDesk.dto.response.UserDtoResponse;
import com.mustapha.medDesk.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    @PreAuthorize("permitAll()")
    public ResponseEntity<ApiResponse<UserDtoResponse>> createUser(
            @Valid @RequestBody UserDtoRequest requestBody,
            HttpServletRequest request) {

        UserDtoResponse user = userService.Create(requestBody);

        ApiResponse<UserDtoResponse> response =
                ApiResponse.success("User created successfully", user);

        response.setStatus(HttpStatus.CREATED.value());
        response.setPath(request.getRequestURI());

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<Page<UserDtoResponse>>> getAllUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            HttpServletRequest request) {

        Page<UserDtoResponse> users = userService.getALl(page, size);

        ApiResponse<Page<UserDtoResponse>> response =
                ApiResponse.success("Users fetched successfully", users);

        response.setStatus(HttpStatus.OK.value());
        response.setPath(request.getRequestURI());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UserDtoResponse>> getUserById(
            @PathVariable Long id,
            HttpServletRequest request) {

        UserDtoResponse user = userService.getById(id);

        ApiResponse<UserDtoResponse> response =
                ApiResponse.success("User fetched successfully", user);

        response.setStatus(HttpStatus.OK.value());
        response.setPath(request.getRequestURI());

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<UserDtoResponse>> updateUser(
            @PathVariable Long id,
            @RequestBody UserUpdateRequest requestBody,
            HttpServletRequest request) {

        UserDtoResponse user = userService.update(id, requestBody);

        ApiResponse<UserDtoResponse> response =
                ApiResponse.success("User updated successfully", user);

        response.setStatus(HttpStatus.OK.value());
        response.setPath(request.getRequestURI());

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteUser(
            @PathVariable Long id,
            HttpServletRequest request) {

        userService.delete(id);

        ApiResponse<Void> response =
                ApiResponse.success("User deleted successfully", null);

        response.setStatus(HttpStatus.OK.value());
        response.setPath(request.getRequestURI());

        return ResponseEntity.ok(response);
    }
}