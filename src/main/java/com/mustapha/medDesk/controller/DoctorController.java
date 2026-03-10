package com.mustapha.medDesk.controller;


import com.mustapha.medDesk.dto.request.doctor.DoctorDtoRequest;
import com.mustapha.medDesk.dto.response.ApiResponse;
import com.mustapha.medDesk.dto.response.doctor.DoctorDtoResponse;
import com.mustapha.medDesk.service.DoctorService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.function.EntityResponse;

import javax.xml.stream.events.EntityReference;

@RestController
@RequiredArgsConstructor
@Getter
@Setter
@RequestMapping("/api/v1/doctors")
public class DoctorController {

    private final DoctorService doctorService;

    @PostMapping
    public ResponseEntity<ApiResponse<DoctorDtoResponse>>create(
            @Valid
            @RequestBody DoctorDtoRequest dto,
            HttpServletRequest request
            ){
        DoctorDtoResponse doctor = doctorService.create(dto);
        ApiResponse<DoctorDtoResponse> response = ApiResponse.success("Doctor Created successfully", doctor);
        response.setStatus(HttpStatus.CREATED.value());
        response.setPath(request.getRequestURI());

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<DoctorDtoResponse>> update(
            @PathVariable Long id,
            @RequestBody DoctorDtoRequest request,
            HttpServletRequest http
    ){
        DoctorDtoResponse doctor = doctorService.update(id, request);

        ApiResponse<DoctorDtoResponse> response = ApiResponse.success("Data updated successfully", doctor);
        response.setStatus(HttpStatus.OK.value());
        response.setPath(http.getRequestURI());

        return ResponseEntity.ok(response);
    }


    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<DoctorDtoResponse>> getById(
            @PathVariable Long id,
            HttpServletRequest http
    ){
        DoctorDtoResponse doctor = doctorService.findById(id);
        ApiResponse<DoctorDtoResponse> response  = ApiResponse.success("User fetched successfully",doctor);
        response.setStatus(HttpStatus.OK.value());
        response.setPath(http.getRequestURI());
        return ResponseEntity.ok(response);
    }


    @GetMapping
    public ResponseEntity<ApiResponse<Page<DoctorDtoResponse>>>getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            HttpServletRequest http
    ){
        Page<DoctorDtoResponse> doctors = doctorService.getAll(page, size);
        ApiResponse<Page<DoctorDtoResponse>> response = ApiResponse.success("Users fetched successfully", doctors);
        response.setStatus(HttpStatus.OK.value());
        response.setPath(http.getRequestURI());
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<ApiResponse<Void>> delete(
            @PathVariable Long id,
            HttpServletRequest http
    ){
      doctorService.delete(id);
       return ResponseEntity.noContent().build();
    }



}
