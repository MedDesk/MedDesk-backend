package com.mustapha.medDesk.dto.response.dashboard;

import lombok.*;
import java.util.Map;
import java.util.List;

@Getter @Setter @Builder @AllArgsConstructor @NoArgsConstructor
public class DashboardStatisticsResponse {
    // Top Row Stats
    private long totalPatients;
    private long totalDoctors;
    private long appointmentsToday;
    private long totalMedicalRecords;

    // Appointment Breakdown (For Pie Charts)
    private Map<String, Long> appointmentStatusBreakdown;

    // Demographic Breakdown (For Bar Charts)
    private Map<String, Long> patientGenderBreakdown;

    // Recent Activity Lists (For Dashboard Tables)
    private List<RecentAppointmentDto> upcomingAppointments;
}

