package com.mustapha.medDesk.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mustapha.medDesk.dto.request.doctor.DoctorDtoRequest;
import com.mustapha.medDesk.enums.DoctorSpeacialist;
import com.mustapha.medDesk.model.Doctor;
import com.mustapha.medDesk.repository.DoctorRepository;
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
@Transactional // this is important to keep dabatabse clean
class DoctorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private DoctorRepository doctorRepository;

    private Doctor savedDoctor;

    @BeforeEach
    void setUp() {
        /**
         * here we prepare a doctor and save him in dabatabse for GET and DELETE tests
         */
        Doctor doctor = new Doctor();
        doctor.setFirstName("Mustapha");
        doctor.setLastName("Moutaki");
        doctor.setEmail("mustapha@meddesk.com");
        doctor.setUsername("dr_mustapha");
        doctor.setPassword("password123");
        doctor.setSpecialist(DoctorSpeacialist.CARDIOLOGY);
        doctor.setLicenseNumber("LIC-12345");

        savedDoctor = doctorRepository.save(doctor);
    }


    @Test
    @WithMockUser(roles = "ADMIN")
    void getById_ShouldReturnDoctor() throws Exception {
        /**
         * we try to get the doctor we saved in setup
         */
        mockMvc.perform(get("/api/v1/doctors/" + savedDoctor.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.firstName").value("Mustapha"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void update_ShouldChangeData() throws Exception {
        /**
         * we try to update doctor's specialist using PATCH
         */
        DoctorDtoRequest req = new DoctorDtoRequest();
        req.setSpecialist(DoctorSpeacialist.NEUROLOGY);

        mockMvc.perform(patch("/api/v1/doctors/" + savedDoctor.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Data updated successfully"))
                .andExpect(jsonPath("$.data.specialist").value("NEUROLOGY"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void getAll_ShouldReturnPage() throws Exception {
        /**
         * check if we can get all doctors list
         */
        mockMvc.perform(get("/api/v1/doctors?page=0&size=10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.content").isArray());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void delete_ShouldReturnNoContent() throws Exception {
        /**
         * testing the delete endpoint (it returns 204)
         */
        mockMvc.perform(delete("/api/v1/doctors/" + savedDoctor.getId()))
                .andExpect(status().isNoContent());
    }
}