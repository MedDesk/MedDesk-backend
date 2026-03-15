package com.mustapha.medDesk.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mustapha.medDesk.dto.request.patient.PatientDtoRequest;
import com.mustapha.medDesk.enums.PatientType;
import com.mustapha.medDesk.model.Patient;
import com.mustapha.medDesk.repository.PatientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional // we roll back dabatabse after test to stay clean
class PatientControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PatientRepository patientRepository;

    private Patient savedPatient;

    @BeforeEach
    void setUp() {
        /**
         * here we prepare a patient and save him in dabatabse
         * we must fill required fields like email and username
         */
        Patient patient = new Patient();
        patient.setFirstName("Mustapha");
        patient.setLastName("Dev");
        patient.setEmail("mustapha@meddesk.com");
        patient.setUsername("mustapha_patient");
        patient.setPassword("pass123");
        patient.setPatientType(PatientType.EMERGENCY);

        savedPatient = patientRepository.save(patient);
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void create_ShouldWork() throws Exception {
        /**
         * we prepare a request to create a new patiend
         * we use a longer password (at least 8 chars) to pass validation
         */
        PatientDtoRequest req = new PatientDtoRequest();
        req.setFirstName("Ahmed");
        req.setLastName("Ali");
        req.setEmail("ahmed@test.com");
        req.setUsername("ahmed_new");
        req.setPassword("password123");
        req.setPatientType(PatientType.EMERGENCY);

        mockMvc.perform(post("/api/v1/patients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andDo(print())
                /**
                 * now it should return 201 Created
                 */
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Patient Created successfully"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void getById_ShouldReturnData() throws Exception {
        /**
         * we try to get the patient we saved in setup by his id
         */
        mockMvc.perform(get("/api/v1/patients/" + savedPatient.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.username").value("mustapha_patient"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void update_ShouldChangePatientData() throws Exception {
        /**
         * we try to update the patient first name
         */
        PatientDtoRequest req = new PatientDtoRequest();
        req.setFirstName("Mustapha Updated");

        mockMvc.perform(patch("/api/v1/patients/" + savedPatient.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Patient updated successfully"))
                .andExpect(jsonPath("$.data.firstName").value("Mustapha Updated"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void getAll_ShouldReturnList() throws Exception {
        /**
         * we check if we can fetch all patients from dabatabse
         */
        mockMvc.perform(get("/api/v1/patients?page=0&size=10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.content").isArray());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void delete_ShouldReturnNoContent() throws Exception {
        /**
         * testing delete method it should return 204 no content
         */
        mockMvc.perform(delete("/api/v1/patients/" + savedPatient.getId()))
                .andExpect(status().isNoContent());
    }
}