package com.mustapha.medDesk.service.impl;

import com.mustapha.medDesk.dto.response.dashboard.DashboardStatisticsResponse;
import com.mustapha.medDesk.dto.response.dashboard.RecentAppointmentDto;
import com.mustapha.medDesk.enums.AppointmentStatus;
import com.mustapha.medDesk.model.Appointment;
import com.mustapha.medDesk.model.Patient;
import com.mustapha.medDesk.repository.AppointmentReposiotry;
import com.mustapha.medDesk.repository.DoctorRepository;
import com.mustapha.medDesk.repository.MedicalRecordRepository;
import com.mustapha.medDesk.repository.PatientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DashboardServiceImplTest {

    @Mock
    private DoctorRepository doctorRepository;

    @Mock
    private PatientRepository patientRepository;

    @Mock
    private AppointmentReposiotry appointmentRepository;

    @Mock
    private MedicalRecordRepository medicalRecordRepository;

    @InjectMocks
    private DashboardServiceImpl dashboardService;

    private Appointment appointment;
    private Patient patient;

    @BeforeEach
    void setUp() {

        patient = new Patient();
        patient.setFirstName("Mustapha");
        patient.setLastName("Moutaki");

        appointment = new Appointment();
        appointment.setId(1L);
        appointment.setPatient(patient);
        appointment.setScheduleTimeStart(LocalTime.of(10, 0));
        appointment.setAppointmentStatus(AppointmentStatus.SCHEDULED);
        appointment.setAppointmentDate(LocalDate.now());
    }

    @Test
    void getStatistics() {
        // ===== Arrange =====
        when(doctorRepository.count()).thenReturn(5L);
        when(patientRepository.count()).thenReturn(50L);
        when(medicalRecordRepository.count()).thenReturn(200L);
        when(appointmentRepository.countByAppointmentDate(any())).thenReturn(10L);

        for (AppointmentStatus status : AppointmentStatus.values()) {
            when(appointmentRepository.countByAppointmentStatus(status)).thenReturn(2L);
        }

        when(appointmentRepository.findTop5ByOrderByAppointmentDateDescScheduleTimeStartDesc())
                .thenReturn(List.of(appointment));

        // ===== Act =====
        DashboardStatisticsResponse response = dashboardService.getStatistics();

        // ===== Assert =====
        assertNotNull(response);
        assertEquals(50, response.getTotalPatients());
        assertEquals(5, response.getTotalDoctors());
        assertEquals(200, response.getTotalMedicalRecords());
        assertEquals(10, response.getAppointmentsToday());

        //  verify appointments status breakdown
        assertEquals(AppointmentStatus.values().length, response.getAppointmentStatusBreakdown().size());


        List<RecentAppointmentDto> recent = response.getUpcomingAppointments();
        assertEquals(1, recent.size());
        assertEquals("Mustapha Moutaki", recent.get(0).getPatientName());


        verify(doctorRepository, times(1)).count();
        verify(patientRepository, times(1)).count();
        verify(medicalRecordRepository, times(1)).count();
        verify(appointmentRepository, times(1)).countByAppointmentDate(any());
        verify(appointmentRepository, times(AppointmentStatus.values().length))
                .countByAppointmentStatus(any());
        verify(appointmentRepository, times(1))
                .findTop5ByOrderByAppointmentDateDescScheduleTimeStartDesc();
    }
}