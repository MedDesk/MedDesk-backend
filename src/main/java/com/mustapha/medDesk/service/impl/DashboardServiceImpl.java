package com.mustapha.medDesk.service.impl;

import com.mustapha.medDesk.dto.response.dashboard.*;
import com.mustapha.medDesk.enums.AppointmentStatus;
import com.mustapha.medDesk.model.Appointment;
import com.mustapha.medDesk.repository.*;
import com.mustapha.medDesk.service.DashbaordService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DashboardServiceImpl implements DashbaordService {

    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;
    private final AppointmentReposiotry appointmentRepository;
    private final MedicalRecordRepository medicalRecordRepository;

    @Override
    public DashboardStatisticsResponse getStatistics() {
        // 1. Basic Counts
        long totalPatients = patientRepository.count();
        long totalDoctors = doctorRepository.count();
        long totalMedRecords = medicalRecordRepository.count();
        long apptsToday = appointmentRepository.countByAppointmentDate(LocalDate.now());

        // 2. Appointment Status Breakdown (For Pie Chart)
        Map<String, Long> statusMap = new HashMap<>();
        for (AppointmentStatus status : AppointmentStatus.values()) {
            statusMap.put(status.name(), appointmentRepository.countByAppointmentStatus(status));
        }

        // 3. Gender Breakdown (Assuming gender exists in Patient model)
        // This is a simple mock, you can use patientRepository.countByGender()
        Map<String, Long> genderMap = new HashMap<>();
        genderMap.put("MALE", 45L); // Replace with repo call
        genderMap.put("FEMALE", 55L);

        // 4. Get 5 Most Recent/Upcoming Appointments
        var recentAppts = appointmentRepository.findTop5ByOrderByAppointmentDateDescScheduleTimeStartDesc()
                .stream()
                .map(app -> RecentAppointmentDto.builder()
                        .id(app.getId())
                        .patientName(app.getPatient().getFirstName() + " " + app.getPatient().getLastName())
                        .time(app.getScheduleTimeStart().toString())
                        .status(app.getAppointmentStatus().name())
                        .build())
                .collect(Collectors.toList());

        return DashboardStatisticsResponse.builder()
                .totalPatients(totalPatients)
                .totalDoctors(totalDoctors)
                .appointmentsToday(apptsToday)
                .totalMedicalRecords(totalMedRecords)
                .appointmentStatusBreakdown(statusMap)
                .patientGenderBreakdown(genderMap)
                .upcomingAppointments(recentAppts)
                .build();
    }
}