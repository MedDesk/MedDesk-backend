package com.mustapha.medDesk.controller;

import com.mustapha.medDesk.dto.request.user.UserDtoReequest;
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
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<ApiResponse<UserDtoResponse>> createUser(@Valid @RequestBody UserDtoReequest requestBody,
                                                                   HttpServletRequest request) {
        UserDtoResponse user = userService.Create(requestBody);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(buildSuccessResponse("User created successfully", user, HttpStatus.CREATED, request.getRequestURI()));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<Page<UserDtoResponse>>> getAllUsers(@RequestParam(defaultValue = "0") int page,
                                                                           @RequestParam(defaultValue = "10") int size,
                                                                           HttpServletRequest request) {
        Page<UserDtoResponse> users = userService.getALl(page, size);
        return ResponseEntity.ok(
                buildSuccessResponse("Users fetched successfully", users, HttpStatus.OK, request.getRequestURI())
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UserDtoResponse>> getUserById(@PathVariable Long id,
                                                                    HttpServletRequest request) {
        UserDtoResponse user = userService.getById(id);
        return ResponseEntity.ok(
                buildSuccessResponse("User fetched successfully", user, HttpStatus.OK, request.getRequestURI())
        );
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<UserDtoResponse>> updateUser(@PathVariable Long id,
                                                                   @RequestBody UserUpdateRequest requestBody,
                                                                   HttpServletRequest request) {
        UserDtoResponse user = userService.update(id, requestBody);
        return ResponseEntity.ok(
                buildSuccessResponse("User updated successfully", user, HttpStatus.OK, request.getRequestURI())
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteUser(@PathVariable Long id, HttpServletRequest request) {
        userService.delete(id);
        return ResponseEntity.ok(
                buildSuccessResponse("User deleted successfully", null, HttpStatus.OK, request.getRequestURI())
        );
    }

    private <T> ApiResponse<T> buildSuccessResponse(String message, T data, HttpStatus status, String path) {
        ApiResponse<T> response = ApiResponse.success(message, data);
        response.setStatus(status.value());
        response.setPath(path);
        return response;
    }
}
