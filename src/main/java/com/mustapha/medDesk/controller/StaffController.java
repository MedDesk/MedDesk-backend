package com.mustapha.medDesk.controller;

import com.mustapha.medDesk.dto.request.staff.StaffDtoRequest;
import com.mustapha.medDesk.dto.request.user.UserDtoRequest;
import com.mustapha.medDesk.dto.response.ApiResponse;
import com.mustapha.medDesk.dto.response.Staff.StaffDtoResponse;
import com.mustapha.medDesk.mapper.StaffMapper;
import com.mustapha.medDesk.service.StaffService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.parser.Entity;
import javax.xml.stream.events.EntityReference;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/staff")
public class StaffController {

    private final StaffService staffService;

    @PostMapping
    public ResponseEntity<ApiResponse<StaffDtoResponse>> create(
            @Valid
            @RequestBody
            StaffDtoRequest requestBody,
            HttpServletRequest request
            ){
        StaffDtoResponse staff = staffService.create(requestBody);
        ApiResponse<StaffDtoResponse> response = ApiResponse.success("staff created successfully", staff);

        response.setStatus(HttpStatus.CREATED.value());
        response.setPath(request.getRequestURI());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<Page<StaffDtoResponse>>> getAllStaff(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            HttpServletRequest request
    ){
        Page<StaffDtoResponse> staff = staffService.getAll(page,size);
        ApiResponse<Page<StaffDtoResponse>> response = ApiResponse.success("staff fetched successfully", staff);
        response.setStatus(HttpStatus.OK.value());
        response.setPath(request.getRequestURI());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<StaffDtoResponse>>getUserById(@PathVariable Long id, HttpServletRequest request){
        StaffDtoResponse staff = staffService.getById(id);
        ApiResponse<StaffDtoResponse> response = ApiResponse.success("staff fetched successfully", staff);
        response.setStatus(HttpStatus.OK.value());
        response.setPath(request.getRequestURI());
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<StaffDtoResponse>> updateStaff(
            @PathVariable Long id,
            @RequestBody StaffDtoRequest requestBody,
            HttpServletRequest request
    ){
        StaffDtoResponse staff = staffService.update(id, requestBody);
        ApiResponse<StaffDtoResponse> response = ApiResponse.success("staff updated successfully", staff);
        response.setStatus(HttpStatus.OK.value());
        response.setPath(request.getRequestURI());
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>>delete(
            @PathVariable Long id,HttpServletRequest request
    ){
         staffService.delete(id);
         ApiResponse<Void> response = ApiResponse.success("staff deleted successfully", null);
         response.setStatus(HttpStatus.OK.value());
         response.setPath(request.getRequestURI());
         return ResponseEntity.ok(response);

    }
}
