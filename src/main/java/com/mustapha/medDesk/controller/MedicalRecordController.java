package com.mustapha.medDesk.controller;

import com.mustapha.medDesk.dto.request.MedicalRecord.MedicalRecordDtoRequest;
import com.mustapha.medDesk.dto.response.ApiResponse;
import com.mustapha.medDesk.dto.response.MedicalRecord.MedicalRecordDtoResponse;
import com.mustapha.medDesk.service.MedicalRecordService;
import com.mustapha.medDesk.service.impl.AppointmentServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/medical-records")
@RequiredArgsConstructor
public class MedicalRecordController {

    private final MedicalRecordService medicalRecordService;
    private final AppointmentServiceImpl appointmentServiceImpl;

    @PostMapping
    public ResponseEntity<ApiResponse<MedicalRecordDtoResponse>> create(
            @Valid @RequestBody MedicalRecordDtoRequest dto,
            HttpServletRequest http
    ) {
        MedicalRecordDtoResponse medicalRecord = medicalRecordService.create(dto);
        ApiResponse<MedicalRecordDtoResponse> response = ApiResponse.success("Medical record created successfully", medicalRecord);
        response.setStatus(HttpStatus.CREATED.value());
        response.setPath(http.getRequestURI());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<MedicalRecordDtoResponse>> update(
            @PathVariable Long id,
            @Valid @RequestBody MedicalRecordDtoRequest dto,
            HttpServletRequest http
    ) {
        MedicalRecordDtoResponse medicalRecord = medicalRecordService.update(id, dto);
        ApiResponse<MedicalRecordDtoResponse> response = ApiResponse.success("Medical record updated successfully", medicalRecord);
        response.setStatus(HttpStatus.OK.value());
        response.setPath(http.getRequestURI());
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<Page<MedicalRecordDtoResponse>>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            HttpServletRequest http
    ) {
        Page<MedicalRecordDtoResponse> records = medicalRecordService.getAll(page, size);
        ApiResponse<Page<MedicalRecordDtoResponse>> response = ApiResponse.success("Medical records retrieved successfully", records);
        response.setStatus(HttpStatus.OK.value());
        response.setPath(http.getRequestURI());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<MedicalRecordDtoResponse>> getById(
            @PathVariable Long id,
            HttpServletRequest http
    ) {
        MedicalRecordDtoResponse record = medicalRecordService.getById(id);
        ApiResponse<MedicalRecordDtoResponse> response = ApiResponse.success("Medical record found", record);
        response.setStatus(HttpStatus.OK.value());
        response.setPath(http.getRequestURI());
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(
            @PathVariable Long id,
            HttpServletRequest http
    ) {
        medicalRecordService.delete(id);
        ApiResponse<Void> response = ApiResponse.success("Medical record deleted successfully", null);
        response.setStatus(HttpStatus.OK.value());
        response.setPath(http.getRequestURI());
        return ResponseEntity.ok(response);
    }


    // get all MedcalREcords for single patient
    @GetMapping("/patient/{patientId}")
    public ResponseEntity<ApiResponse<List<MedicalRecordDtoResponse>>> getAllByPatientId(
            @PathVariable Long patientId,
            HttpServletRequest http
    ){
        List<MedicalRecordDtoResponse> history = medicalRecordService.getPatientHistory(patientId);
        return ResponseEntity.ok(ApiResponse.success("Patient history fetched successfully", history));

    }
}