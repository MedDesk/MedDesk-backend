package com.mustapha.medDesk.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mustapha.medDesk.dto.request.MedicalRecord.MedicalRecordDtoRequest;
import com.mustapha.medDesk.enums.*;
import com.mustapha.medDesk.model.*;
import com.mustapha.medDesk.repository.*;
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
@Transactional // this keeps the dabatabse clean after tests
class MedicalRecordControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MedicalRecordRepository medicalRecordRepository;
    @Autowired
    private PatientRepository patientRepository;
    @Autowired
    private DoctorRepository doctorRepository;
    @Autowired
    private StaffRepository staffRepository;
    @Autowired
    private AppointmentReposiotry appointmentReposiotry;

    private Patient savedPatient;
    private Doctor savedDoctor;
    private Staff savedNurse;
    private Appointment savedAppointment;
    private MedicalRecord savedRecord;

    @BeforeEach
    void setUp() {
        /**
         * 1. Setup Nurse (Staff)
         * we fill every mandatory field to make dabatabse happy
         */
        Staff nurse = new Staff();
        nurse.setFirstName("Joy");
        nurse.setLastName("Nurse");
        nurse.setUsername("nurse_joy");
        nurse.setEmail("joy@meddesk.com");
        nurse.setPassword("pass123");
        nurse.setStaffType(StaffType.NURSE);
        nurse.setEmploymentStatus(EmploymentStatus.ACTIVE);
        nurse.setContractType(ContractType.FULL_TIME);
        nurse.setShiftPreference(ShiftPreference.DAY);

        // we add this line to fix the new error!
        nurse.setStaffSpecialist(StaffSpecialist.ANESTHESIOLOGIST); // check your Enum name

        savedNurse = staffRepository.save(nurse);

        /**
         * 2. Setup Doctor
         */
        Doctor doctor = new Doctor();
        doctor.setFirstName("Mustapha");
        doctor.setLastName("Doc");
        doctor.setUsername("dr_mustapha");
        doctor.setEmail("mustapha@meddesk.com");
        doctor.setPassword("pass123");
        doctor.setSpecialist(DoctorSpeacialist.CARDIOLOGY);
        doctor.setLicenseNumber("LIC-999");
        savedDoctor = doctorRepository.save(doctor);

        /**
         * 3. Setup Patient
         */
        Patient patient = new Patient();
        patient.setFirstName("John");
        patient.setLastName("Doe");
        patient.setUsername("john_doe");
        patient.setEmail("john@example.com");
        patient.setPassword("pass123");
        savedPatient = patientRepository.save(patient);

        /**
         * 4. Setup Appointment
         */
        Appointment app = new Appointment();
        app.setPatient(savedPatient);
        app.setAppointmentDate(LocalDate.now());
        app.setScheduleTimeStart(LocalTime.of(10, 0));
        app.setScheduleTimeEnd(LocalTime.of(10, 30));
        app.setAppointmentStatus(AppointmentStatus.SCHEDULED);
        savedAppointment = appointmentReposiotry.save(app);

        /**
         * 5. Setup Medical Record for GET/DELETE tests
         */
        MedicalRecord record = new MedicalRecord();
        record.setNurse(savedNurse);
        record.setDoctor(savedDoctor);
        record.setAppointment(savedAppointment);
        savedRecord = medicalRecordRepository.save(record);
    }


    @Test
    @WithMockUser(roles = "ADMIN")
    void getById_ShouldReturnRecord() throws Exception {
        /**
         * we check if we can find the record we saved in setup
         */
        mockMvc.perform(get("/api/v1/medical-records/" + savedRecord.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.id").value(savedRecord.getId()));
    }



    @Test
    @WithMockUser(roles = "ADMIN")
    void delete_ShouldReturnSuccess() throws Exception {
        /**
         * we call delete and check if it return ok response
         */
        mockMvc.perform(delete("/api/v1/medical-records/" + savedRecord.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Medical record deleted successfully"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void getAllByPatientId_ShouldReturnHistory() throws Exception {
        /**
         * we check if we can get all records for our patient john
         */
        mockMvc.perform(get("/api/v1/medical-records/patient/" + savedPatient.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data").isArray());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void getAll_ShouldReturnPage() throws Exception {
        /**
         * we check the pagination for all medical records
         */
        mockMvc.perform(get("/api/v1/medical-records?page=0&size=10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.content").isArray());
    }
}