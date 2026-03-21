package com.mustapha.medDesk.service.impl;

import com.mustapha.medDesk.dto.request.auth.register.RegisterDtoRequest;
import com.mustapha.medDesk.dto.request.auth.signup.LoginDtoRequest;
import com.mustapha.medDesk.mapper.UserMapper;
import com.mustapha.medDesk.model.User;
import com.mustapha.medDesk.repository.RefreshTokenRepository;
import com.mustapha.medDesk.repository.UserRepository;
import com.mustapha.medDesk.security.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private JwtService jwtService;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private UserMapper userMapper;

    @Mock
    private RefreshTokenRepository refreshTokenRepository;

    @InjectMocks
    private AuthServiceImpl authService;

    private RegisterDtoRequest registerDtoRequest;
    private LoginDtoRequest loginDtoRequest;
    private User user;

    @BeforeEach
    void setUp(){
        registerDtoRequest = new RegisterDtoRequest();
        registerDtoRequest.setFirstName("mustapha");
        registerDtoRequest.setLastName("moutaki");
        registerDtoRequest.setUsername("mustapha_001");
        registerDtoRequest.setEmail("mustaphaamoutaki@gmail.com");
        registerDtoRequest.setPassword("password");

        loginDtoRequest = new LoginDtoRequest();
        loginDtoRequest.setEmail("mustaphaamoutaki@gmail.com");
        loginDtoRequest.setPassword("password");

        user = new User();
        user.setEmail("mustaphaamoutaki@gmail.com");
        user.setUsername("mustapha_001");
    }

    @Test
    void register() {
        // Arrange
        when(userRepository.existsByEmail(any())).thenReturn(false);
        when(userRepository.save(any())).thenReturn(user);
        when(jwtService.generateAccessToken(any())).thenReturn("fake-access-token");
        when(jwtService.generateRefreshToken(any())).thenReturn("fake-refresh-token");

        // Act
        var response = authService.register(registerDtoRequest);

        // Assert
        assertNotNull(response);
        verify(userRepository, times(1)).save(any());
        verify(jwtService, times(1)).generateAccessToken(any());
        verify(refreshTokenRepository, times(1)).save(any());
    }

    @Test
    void login() {
        // Arrange
        when(userRepository.findByEmail(any())).thenReturn(Optional.of(user));
        when(jwtService.generateAccessToken(any())).thenReturn("fake-access-token");
        when(jwtService.generateRefreshToken(any())).thenReturn("fake-refresh-token");

        // Act
        var response = authService.Login(loginDtoRequest);

        // Assert
        assertNotNull(response);
        verify(authenticationManager, times(1)).authenticate(any());
        verify(jwtService, times(1)).generateAccessToken(any());
        verify(refreshTokenRepository, times(1)).save(any());
    }
}