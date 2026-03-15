package com.mustapha.medDesk.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mustapha.medDesk.dto.request.appointment.AppointmentDtoRequest;
import com.mustapha.medDesk.enums.AppointmentStatus;
import com.mustapha.medDesk.enums.DayOfWeek;
import com.mustapha.medDesk.enums.PatientType;
import com.mustapha.medDesk.model.Appointment;
import com.mustapha.medDesk.model.Patient;
import com.mustapha.medDesk.model.WorkingHours;
import com.mustapha.medDesk.repository.AppointmentReposiotry;
import com.mustapha.medDesk.repository.PatientRepository;
import com.mustapha.medDesk.repository.WorkingHoursRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional // this roll back dabatabse after test
class AppointmentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private WorkingHoursRepository workingHoursRepository;

    @Autowired
    private AppointmentReposiotry appointmentReposiotry;

    private Patient savedPatient;
    private Appointment savedAppointment;

    @BeforeEach
    void setUp() {
        /**
         * we prepare patient with all fields for dabatabse
         */
        Patient p = new Patient();
        p.setFirstName("John");
        p.setLastName("Doe");
        p.setEmail("john.doe@test.com");
        p.setUsername("john_doe_test");
        p.setPassword("password123");
        p.setPatientType(PatientType.EMERGENCY);
        savedPatient = patientRepository.save(p);

        /**
         * we set working hours for monday
         */
        WorkingHours wh = new WorkingHours();
        wh.setDayOfWeek(DayOfWeek.MONDAY);
        wh.setStartTime(LocalTime.of(9, 0));
        wh.setEndTime(LocalTime.of(17, 0));
        wh.setActive(true);
        workingHoursRepository.save(wh);

        /**
         * we save one appointment in dabatabse for testing
         */
        Appointment app = new Appointment();
        app.setPatient(savedPatient);
        app.setAppointmentDate(LocalDate.of(2025, 2, 24)); // Monday
        app.setScheduleTimeStart(LocalTime.of(10, 0));
        app.setScheduleTimeEnd(LocalTime.of(10, 30));
        app.setAppointmentStatus(AppointmentStatus.SCHEDULED);
        savedAppointment = appointmentReposiotry.save(app);
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void create_ShouldWork() throws Exception {
        /**
         * we call POST /api/v1/appointments
         */
        AppointmentDtoRequest req = new AppointmentDtoRequest();
        req.setPatientId(savedPatient.getId());
        req.setAppointmentDate(LocalDate.of(2025, 2, 24));
        req.setScheduleTimeStart(LocalTime.of(11, 0));
        req.setAppointmentStatus(AppointmentStatus.SCHEDULED);

        mockMvc.perform(post("/api/v1/appointments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isCreated()); // your controller returns 201 Created
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void getById_ShouldReturnAppointment() throws Exception {
        /**
         * we call GET /api/v1/appointments/{id}
         */
        mockMvc.perform(get("/api/v1/appointments/" + savedAppointment.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(savedAppointment.getId()));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void getAvailability_ShouldWork() throws Exception {
        /**
         * we call GET /api/v1/appointments/availability
         */
        mockMvc.perform(get("/api/v1/appointments/availability")
                        .param("date", "2025-02-24"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void getAll_ShouldReturnPage() throws Exception {
        /**
         * we call GET /api/v1/appointments
         */
        mockMvc.perform(get("/api/v1/appointments"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray());
    }



    @Test
    @WithMockUser(roles = "ADMIN")
    void delete_ShouldWork() throws Exception {
        /**
         * we call DELETE /api/v1/appointments/{id}
         */
        mockMvc.perform(delete("/api/v1/appointments/" + savedAppointment.getId()))
                .andExpect(status().isNoContent()); // your controller returns 204 No Content
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void getWeeklyAvailability_ShouldWork() throws Exception {
        /**
         * we call GET /api/v1/appointments/weekly-availability
         */
        mockMvc.perform(get("/api/v1/appointments/weekly-availability"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void getByPatientId_ShouldWork() throws Exception {
        /**
         * we call GET /api/v1/appointments/patient/{id}
         */
        mockMvc.perform(get("/api/v1/appointments/patient/" + savedPatient.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }
}