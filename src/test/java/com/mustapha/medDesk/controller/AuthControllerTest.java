package com.mustapha.medDesk.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mustapha.medDesk.dto.request.auth.register.RegisterDtoRequest;
import com.mustapha.medDesk.dto.request.auth.signup.LoginDtoRequest;
import com.mustapha.medDesk.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional // clean dabatabse after every test
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    @Test
    void register_ShouldWork_AndCreateUser() throws Exception {
        /**
         * we prepare the register request with new user data
         */
        RegisterDtoRequest req = new RegisterDtoRequest();
        req.setFirstName("Mustapha");
        req.setLastName("Dev");
        req.setEmail("mustapha.dev@test.com");
        req.setUsername("mustapha_admin");
        req.setPassword("password123");

        /**
         * we call POST /api/v1/auth/register
         */
        mockMvc.perform(post("/api/v1/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andDo(print())
                /**
                 * we check if success is true and message is correct
                 * based on your ApiResponse structure
                 */
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Registration successful"));
    }

    @Test
    void login_ShouldWork_AndReturnToken() throws Exception {
        /**
         * 1. first we must register a user in dabatabse
         */
        RegisterDtoRequest reg = new RegisterDtoRequest();
        reg.setFirstName("John");
        reg.setLastName("Doe");
        reg.setEmail("john@test.com");
        reg.setUsername("john_doe");
        reg.setPassword("password123");

        mockMvc.perform(post("/api/v1/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(reg)));

        /**
         * 2. now we try to login with the same username and password
         */
        LoginDtoRequest loginReq = new LoginDtoRequest();
        loginReq.setEmail("john_doe@gmail.com");
        loginReq.setPassword("password123");

        mockMvc.perform(post("/api/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginReq)))
                .andDo(print())
                /**
                 * we check if we get a token in the data object
                 */
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.token").exists());
    }

    @Test
    void login_ShouldFail_WhenPasswordIsWrong() throws Exception {
        /**
         * we try to login with wrong credentials
         */
        LoginDtoRequest loginReq = new LoginDtoRequest();
        loginReq.setEmail("wrongEmail@gmail.com");
        loginReq.setPassword("wrong_pass");

        mockMvc.perform(post("/api/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginReq)))
                .andDo(print())
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").value("Bad credentials"));
    }
}