package com.mustapha.medDesk.controller;

import com.mustapha.medDesk.dto.request.Vital.VitalDtoRequest;
import com.mustapha.medDesk.dto.response.ApiResponse;

import com.mustapha.medDesk.dto.response.vital.VitalDtoResponse;
import com.mustapha.medDesk.service.VitalService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/vitals")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('ADMIN','SUPER_ADMIN', 'RECEPTIONIST')")
public class VitalController {


    private final VitalService vitalService;

    @PostMapping
    public ResponseEntity<ApiResponse<VitalDtoResponse>> create(
            @Valid @RequestBody VitalDtoRequest dto,
            HttpServletRequest http
    ) {
        VitalDtoResponse responseData = vitalService.create(dto);
        ApiResponse<VitalDtoResponse> response = ApiResponse.success("Patient vitals recorded successfully", responseData);
        response.setStatus(HttpStatus.CREATED.value());
        response.setPath(http.getRequestURI());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<VitalDtoResponse>> update(
            @PathVariable Long id,
            @Valid @RequestBody VitalDtoRequest dto,
            HttpServletRequest http
    ) {
        VitalDtoResponse responseData = vitalService.update(id, dto);
        ApiResponse<VitalDtoResponse> response = ApiResponse.success("Patient vitals updated successfully", responseData);
        response.setStatus(HttpStatus.OK.value());
        response.setPath(http.getRequestURI());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<VitalDtoResponse>> getById(
            @PathVariable Long id,
            HttpServletRequest http
    ) {
        VitalDtoResponse responseData = vitalService.getById(id);
        ApiResponse<VitalDtoResponse> response = ApiResponse.success("Vitals retrieved successfully", responseData);
        response.setStatus(HttpStatus.OK.value());
        response.setPath(http.getRequestURI());
        return ResponseEntity.ok(response);
    }


    @GetMapping
    public ResponseEntity<ApiResponse<List<VitalDtoResponse>>> getAll(HttpServletRequest http) {
        List<VitalDtoResponse> responseData = vitalService.getAll();
        ApiResponse<List<VitalDtoResponse>> response = ApiResponse.success("All vitals retrieved successfully", responseData);
        response.setStatus(HttpStatus.OK.value());
        response.setPath(http.getRequestURI());
        return ResponseEntity.ok(response);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(
            @PathVariable Long id,
            HttpServletRequest http
    ) {
        vitalService.delete(id);
        ApiResponse<Void> response = ApiResponse.success("Vitals record deleted successfully", null);
        response.setStatus(HttpStatus.OK.value());
        response.setPath(http.getRequestURI());
        return ResponseEntity.ok(response);
    }



}

