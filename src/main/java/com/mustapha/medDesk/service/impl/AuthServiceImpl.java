package com.mustapha.medDesk.service.impl;

import com.mustapha.medDesk.dto.request.auth.register.RegisterDtoRequest;
import com.mustapha.medDesk.dto.request.auth.signup.LoginDtoRequest;
import com.mustapha.medDesk.dto.response.auth.AuthDtoResponse;
import com.mustapha.medDesk.enums.UserRole;
import com.mustapha.medDesk.exception.ValidationException;
import com.mustapha.medDesk.mapper.UserMapper;
import com.mustapha.medDesk.model.User;
import com.mustapha.medDesk.repository.UserRepository;
import com.mustapha.medDesk.security.CustomUserDetails;
import com.mustapha.medDesk.security.JwtService;
import com.mustapha.medDesk.service.AuthService;
import com.mustapha.medDesk.util.PasswordUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserMapper userMapper; // Injecting your existing UserMapper

    @Override
    @Transactional
    public AuthDtoResponse register(RegisterDtoRequest dto) {
        // 1. Check if email is already taken
        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new ValidationException("Email is already in use");
        }

        // 2. Handle username logic (If blank, use email)
        String username = (dto.getUsername() == null || dto.getUsername().isBlank())
                ? dto.getEmail() : dto.getUsername();

        // 3. Create User Entity
        User user = User.builder()
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .email(dto.getEmail())
                .username(username)
                .password(PasswordUtil.hash(dto.getPassword()))
                .role(UserRole.PATIENT)
                .build();

        // Save User
        User savedUser = userRepository.save(user);

        // Generate Tokens
        CustomUserDetails userDetails = new CustomUserDetails(savedUser);
        String accessToken = jwtService.generateAccessToken(userDetails);
        String refreshToken = jwtService.generateRefreshToken(userDetails);

        // Return response using UserMapper for the user part
        return AuthDtoResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .user(userMapper.toDto(savedUser))
                .build();
    }

    @Override
    public AuthDtoResponse Login(LoginDtoRequest dto) {
        // 1. Trigger Spring Security Authentication
        // This automatically checks the email and password (using BCrypt)
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getPassword())
        );

        // 2. Fetch the User from DB
        User user = userRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new ValidationException("Invalid email or password"));

        // 3. Generate Tokens
        CustomUserDetails userDetails = new CustomUserDetails(user);
        String accessToken = jwtService.generateAccessToken(userDetails);
        String refreshToken = jwtService.generateRefreshToken(userDetails);

        // 4. Return response
        return AuthDtoResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .user(userMapper.toDto(user))
                .build();
    }
}