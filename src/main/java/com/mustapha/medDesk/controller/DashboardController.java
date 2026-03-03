package com.mustapha.medDesk.controller;


import com.mustapha.medDesk.dto.response.dashboard.DashboardStatisticsResponse;
import com.mustapha.medDesk.service.DashbaordService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final DashbaordService dashboardService;

    @GetMapping("/statistics")
    public ResponseEntity<DashboardStatisticsResponse> getStatistics() {
        return ResponseEntity.ok(dashboardService.getStatistics());
    }
}