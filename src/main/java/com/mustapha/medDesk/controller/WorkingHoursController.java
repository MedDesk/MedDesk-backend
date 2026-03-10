package com.mustapha.medDesk.controller;


import com.mustapha.medDesk.dto.request.workingHour.WorkingHoursDtoRequest;
import com.mustapha.medDesk.dto.response.ApiResponse;
import com.mustapha.medDesk.dto.response.workingHours.WorkingHoursDtoResponse;
import com.mustapha.medDesk.service.WorkingHoursService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.function.EntityResponse;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/workingHours")
@PreAuthorize("hasAnyRole('ADMIN','SUPER_ADMIN', 'RECEPTIONIST')")
public class WorkingHoursController {

    private final WorkingHoursService workingHoursService;

    @PostMapping
    public ResponseEntity<ApiResponse<WorkingHoursDtoResponse>> create(
            @Valid
            @RequestBody WorkingHoursDtoRequest workingHoursDtoRequest,
            HttpServletRequest http
            ){
        WorkingHoursDtoResponse working = workingHoursService.create(workingHoursDtoRequest);
        ApiResponse<WorkingHoursDtoResponse> response = ApiResponse.success("Working time scheduled successfully", working);
        response.setStatus(HttpStatus.CREATED.value());
        response.setPath(http.getRequestURI());

        return ResponseEntity.status(HttpStatus.CREATED.value()).body(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<WorkingHoursDtoResponse>> update(
            @Valid
            @PathVariable Long id,
            @RequestBody WorkingHoursDtoRequest request,
            HttpServletRequest http
    ){
        WorkingHoursDtoResponse savedWorkingHours = workingHoursService.update(id, request);
        ApiResponse<WorkingHoursDtoResponse> response =  ApiResponse.success("working hours updated successfully", savedWorkingHours);
        response.setStatus(HttpStatus.OK.value());
        response.setPath(http.getRequestURI());

        return ResponseEntity.ok(response);

    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<WorkingHoursDtoResponse>> getById(
            @PathVariable Long id,
            HttpServletRequest http
    ){
        WorkingHoursDtoResponse workingHours = workingHoursService.getById(id);
        ApiResponse<WorkingHoursDtoResponse> response = ApiResponse.success("Working Hours fetched successfully", workingHours);
        response.setPath(http.getRequestURI());
        response.setStatus(HttpStatus.OK.value());

        return ResponseEntity.ok(response);
    }


    @GetMapping
    public ResponseEntity<ApiResponse<List<WorkingHoursDtoResponse>>> getAll(
            HttpServletRequest http
    ){
        List<WorkingHoursDtoResponse> workingHours =  workingHoursService.getAll();
        ApiResponse<List<WorkingHoursDtoResponse>> response = ApiResponse.success("Working hours fetched successfully", workingHours);
        response.setStatus(HttpStatus.OK.value());
        response.setPath(http.getRequestURI());

        return ResponseEntity.ok(response);
    }



    @DeleteMapping("{id}")
    public ResponseEntity<ApiResponse<Void>>delete(
            @PathVariable Long id
    ){
        workingHoursService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
