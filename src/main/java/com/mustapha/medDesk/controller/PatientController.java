package com.mustapha.medDesk.controller;

import com.mustapha.medDesk.dto.request.patient.PatientDtoRequest;
import com.mustapha.medDesk.dto.response.ApiResponse;
import com.mustapha.medDesk.dto.response.patient.PatientDtoResponse;
import com.mustapha.medDesk.model.Patient;
import com.mustapha.medDesk.service.PatientService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/patients")
public class PatientController {

    private final PatientService patientService;

    @PostMapping
    public ResponseEntity<ApiResponse<PatientDtoResponse>> create(
            // valid data
        @Valid
        @RequestBody PatientDtoRequest dto,
        HttpServletRequest request
    ){
        // save data
        PatientDtoResponse savedPatient = patientService.create(dto);
        // canpsul data
        ApiResponse response = ApiResponse.success("Patient Created successfully", savedPatient);
        response.setStatus(HttpStatus.CREATED.value());
        response.setPath(request.getRequestURI());
        //return data
        return ResponseEntity.status(HttpStatus.CREATED).body(response);

    }


    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<PatientDtoResponse>>update(
            @PathVariable Long id,
            @Valid
            @RequestBody PatientDtoRequest dto,
            HttpServletRequest request
    ){
        PatientDtoResponse updatedPatient = patientService.update(id, dto);
        ApiResponse<PatientDtoResponse> response = ApiResponse.success("Patient updated successfully", updatedPatient);
        response.setStatus(HttpStatus.OK.value());
        response.setPath(request.getRequestURI());

        return ResponseEntity.ok(response);
    }


    @GetMapping
    public ResponseEntity<ApiResponse<Page<PatientDtoResponse>>>getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            HttpServletRequest request
    ){
        Page<PatientDtoResponse> patients = patientService.getAll(page, size);
        ApiResponse<Page<PatientDtoResponse>> response = ApiResponse.success("Patients fetched successfully", patients);
        response.setStatus(HttpStatus.OK.value());
        response.setPath(request.getRequestURI());

        return ResponseEntity.ok(response);

    }


    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<PatientDtoResponse>> getById(
            @PathVariable Long id,
            HttpServletRequest request
    ){
        PatientDtoResponse patient = patientService.getById(id);
        ApiResponse<PatientDtoResponse> response = ApiResponse.success("patiend fetched successfully ", patient);
        response.setStatus(HttpStatus.OK.value());
        response.setPath(request.getRequestURI());
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<ApiResponse<Void>> delete(
            @PathVariable Long id,
            HttpServletRequest request
    ){
        patientService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
