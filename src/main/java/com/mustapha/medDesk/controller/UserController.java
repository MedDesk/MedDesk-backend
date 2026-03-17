//package com.mustapha.medDesk.controller;
//
//import com.mustapha.medDesk.dto.request.user.UserDtoRequest;
//import com.mustapha.medDesk.dto.request.user.UserUpdateRequest;
//import com.mustapha.medDesk.dto.response.ApiResponse;
//import com.mustapha.medDesk.dto.response.user.UserDtoResponse;
//import com.mustapha.medDesk.service.UserService;
//
//import io.micrometer.core.instrument.MeterRegistry;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.validation.Valid;
//import lombok.RequiredArgsConstructor;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.data.domain.Page;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.web.bind.annotation.*;
//
//
//@RestController
//@RequestMapping("/api/v1/users")
//@RequiredArgsConstructor
//public class UserController {
//
//    private final UserService userService;
//    private final Logger logger = LoggerFactory.getLogger(UserController.class);
//    private final MeterRegistry meterRegistry;
//
//
//    @PostMapping
//    @PreAuthorize("permitAll()")
//    public ResponseEntity<ApiResponse<UserDtoResponse>> createUser(
//            @Valid @RequestBody UserDtoRequest requestBody,
//            HttpServletRequest request) {
//
//        UserDtoResponse user = userService.Create(requestBody);
//
//        ApiResponse<UserDtoResponse> response =
//                ApiResponse.success("User created successfully", user);
//
//        response.setStatus(HttpStatus.CREATED.value());
//        response.setPath(request.getRequestURI());
//
//        return ResponseEntity.status(HttpStatus.CREATED).body(response);
//    }
//
//    @GetMapping
//    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
//    public ResponseEntity<ApiResponse<Page<UserDtoResponse>>> getAllUsers(
//            @RequestParam(defaultValue = "0") int page,
//            @RequestParam(defaultValue = "10") int size,
//            HttpServletRequest request
//    ) {
//        logger.info("Fetching all users, page: {}, size: {}", page, size);
//        Page<UserDtoResponse> users = userService.getALl(page, size);
//
//        ApiResponse<Page<UserDtoResponse>> response =
//                ApiResponse.success("Doctors fetched successfully", users);
//
//        response.setStatus(HttpStatus.OK.value());
//        response.setPath(request.getRequestURI());
//        logger.info("Fetched {} users", users.getTotalElements());
//
//        return ResponseEntity.ok(response);
//    }
//
//    @GetMapping("/{id}")
//    // the the those role or the Id in the url is my own
//    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN') or #id == authentication.principal.id")
//    public ResponseEntity<ApiResponse<UserDtoResponse>> getUserById(
//            @PathVariable Long id,
//            HttpServletRequest request) {
//
//        UserDtoResponse user = userService.getById(id);
//
//        ApiResponse<UserDtoResponse> response =
//                ApiResponse.success("Doctor fetched successfully", user);
//
//        response.setStatus(HttpStatus.OK.value());
//        response.setPath(request.getRequestURI());
//
//        return ResponseEntity.ok(response);
//    }
//
//    @PatchMapping("/{id}")
//    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN') or #id == authentication.principal.id")
//    public ResponseEntity<ApiResponse<UserDtoResponse>> updateUser(
//            @PathVariable Long id,
//            @RequestBody UserUpdateRequest requestBody,
//            HttpServletRequest request) {
//
//        UserDtoResponse user = userService.update(id, requestBody);
//
//        ApiResponse<UserDtoResponse> response =
//                ApiResponse.success("Doctor updated successfully", user);
//
//        response.setStatus(HttpStatus.OK.value());
//        response.setPath(request.getRequestURI());
//
//        return ResponseEntity.ok(response);
//    }
//
//    @DeleteMapping("/{id}")
//    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
//    public ResponseEntity<ApiResponse<Void>> deleteUser(
//            @PathVariable Long id,
//            HttpServletRequest request) {
//
//        userService.delete(id);
//
//        ApiResponse<Void> response =
//                ApiResponse.success("Doctor deleted successfully", null);
//
//        response.setStatus(HttpStatus.OK.value());
//        response.setPath(request.getRequestURI());
//
//        return ResponseEntity.ok(response);
//    }
//}
package com.mustapha.medDesk.controller;

import com.mustapha.medDesk.dto.request.user.UserDtoRequest;
import com.mustapha.medDesk.dto.request.user.UserUpdateRequest;
import com.mustapha.medDesk.dto.response.ApiResponse;
import com.mustapha.medDesk.dto.response.user.UserDtoResponse;
import com.mustapha.medDesk.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;

import jakarta.annotation.PostConstruct;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final MeterRegistry meterRegistry; // injected automatically
    private final Logger logger = LoggerFactory.getLogger(UserController.class);

    private Counter createUserCounter;
    private Counter getAllUsersCounter;
    private Counter getUserByIdCounter;
    private Counter updateUserCounter;
    private Counter deleteUserCounter;

    @PostConstruct
    public void initCounters() {
        createUserCounter = Counter.builder("users_endpoint_requests_total")
                .tag("endpoint", "createUser")
                .description("Number of calls to createUser")
                .register(meterRegistry);

        getAllUsersCounter = Counter.builder("users_endpoint_requests_total")
                .tag("endpoint", "getAllUsers")
                .description("Number of calls to getAllUsers")
                .register(meterRegistry);

        getUserByIdCounter = Counter.builder("users_endpoint_requests_total")
                .tag("endpoint", "getUserById")
                .description("Number of calls to getUserById")
                .register(meterRegistry);

        updateUserCounter = Counter.builder("users_endpoint_requests_total")
                .tag("endpoint", "updateUser")
                .description("Number of calls to updateUser")
                .register(meterRegistry);

        deleteUserCounter = Counter.builder("users_endpoint_requests_total")
                .tag("endpoint", "deleteUser")
                .description("Number of calls to deleteUser")
                .register(meterRegistry);
    }

    @PostMapping
    @PreAuthorize("permitAll()")
    public ResponseEntity<ApiResponse<UserDtoResponse>> createUser(
            @Valid @RequestBody UserDtoRequest requestBody,
            HttpServletRequest request) {

        createUserCounter.increment();
        logger.info("Accessed createUser endpoint");

        UserDtoResponse user = userService.Create(requestBody);
        ApiResponse<UserDtoResponse> response = ApiResponse.success("User created successfully", user);
        response.setStatus(HttpStatus.CREATED.value());
        response.setPath(request.getRequestURI());

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<ApiResponse<Page<UserDtoResponse>>> getAllUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            HttpServletRequest request) {

        getAllUsersCounter.increment();
        logger.info("Accessed getAllUsers endpoint");

        Page<UserDtoResponse> users = userService.getALl(page, size);
        ApiResponse<Page<UserDtoResponse>> response = ApiResponse.success("Users fetched successfully", users);
        response.setStatus(HttpStatus.OK.value());
        response.setPath(request.getRequestURI());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN') or #id == authentication.principal.id")
    public ResponseEntity<ApiResponse<UserDtoResponse>> getUserById(
            @PathVariable Long id,
            HttpServletRequest request) {

        getUserByIdCounter.increment();
        logger.info("Accessed getUserById endpoint: id={}", id);

        UserDtoResponse user = userService.getById(id);
        ApiResponse<UserDtoResponse> response = ApiResponse.success("User fetched successfully", user);
        response.setStatus(HttpStatus.OK.value());
        response.setPath(request.getRequestURI());

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN') or #id == authentication.principal.id")
    public ResponseEntity<ApiResponse<UserDtoResponse>> updateUser(
            @PathVariable Long id,
            @RequestBody UserUpdateRequest requestBody,
            HttpServletRequest request) {

        updateUserCounter.increment();
        logger.info("Accessed updateUser endpoint: id={}", id);

        UserDtoResponse user = userService.update(id, requestBody);
        ApiResponse<UserDtoResponse> response = ApiResponse.success("User updated successfully", user);
        response.setStatus(HttpStatus.OK.value());
        response.setPath(request.getRequestURI());

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<ApiResponse<Void>> deleteUser(
            @PathVariable Long id,
            HttpServletRequest request) {

        deleteUserCounter.increment();
        logger.info("Accessed deleteUser endpoint: id={}", id);

        userService.delete(id);
        ApiResponse<Void> response = ApiResponse.success("User deleted successfully", null);
        response.setStatus(HttpStatus.OK.value());
        response.setPath(request.getRequestURI());

        return ResponseEntity.ok(response);
    }
}