package com.mustapha.medDesk.service.impl;

import com.mustapha.medDesk.dto.request.auth.register.RegisterDtoRequest;
import com.mustapha.medDesk.dto.request.auth.signup.LoginDtoRequest;
import com.mustapha.medDesk.dto.response.auth.AuthDtoResponse;
import com.mustapha.medDesk.enums.UserRole;
import com.mustapha.medDesk.exception.ValidationException;
import com.mustapha.medDesk.mapper.UserMapper;
import com.mustapha.medDesk.model.RefreshToken;
import com.mustapha.medDesk.model.User;
import com.mustapha.medDesk.repository.RefreshTokenRepository;
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

import java.util.Date;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserMapper userMapper; // Injecting your existing UserMapper
    private final RefreshTokenRepository refreshTokenRepository;


    @Override
    @Transactional
    public AuthDtoResponse register(RegisterDtoRequest dto) {
        //  Check if email is already taken
        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new ValidationException("Email is already in use");
        }

        // Handle username logic (If blank, use email)
        String username = (dto.getUsername() == null || dto.getUsername().isBlank())
                ? dto.getEmail() : dto.getUsername();

        //  Create User Entity
        User user = User.builder()
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .email(dto.getEmail())
                .username(username)
                .password(PasswordUtil.hash(dto.getPassword()))
                .role(UserRole.PATIENT)
                .build();
        /**
         * we can't use build to set crreateBy becaues is not support inheritence for baseEntity
         * so we can use the superBuilder or set it using setters
         */
        user.setCreatedBy("System");
        // Save User
        User savedUser = userRepository.save(user);

        // Generate Tokens
        CustomUserDetails userDetails = new CustomUserDetails(savedUser);
        String accessToken = jwtService.generateAccessToken(userDetails);
        String refreshToken = jwtService.generateRefreshToken(userDetails);
        saveRefreshToken(refreshToken, savedUser.getEmail());
        // Return response using UserMapper for the user part
        return AuthDtoResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .user(userMapper.toDto(savedUser))
                .build();
    }

    @Override
    public AuthDtoResponse Login(LoginDtoRequest dto) {

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
        saveRefreshToken(refreshToken, user.getEmail()); // stoe the refresh token in the db
        // 4. Return response
        return AuthDtoResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .user(userMapper.toDto(user))
                .build();
    }


    private void saveRefreshToken(String token, String email) {
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setToken(token);
        refreshToken.setEmail(email);
        refreshToken.setRevoked(false);
        refreshToken.setExpiryDate(
                new Date(System.currentTimeMillis() + 7 * 24 * 60 * 60 * 1000) // 7 days
        );

        refreshTokenRepository.save(refreshToken);
    }


    @Override
    public AuthDtoResponse refreshToken(String refreshToken) {

        // 1. check if exists in DB
        RefreshToken storedToken = refreshTokenRepository.findByToken(refreshToken)
                .orElseThrow(() -> new RuntimeException("Invalid refresh token"));

        // 2. check revoked
        if (storedToken.isRevoked()) {
            throw new RuntimeException("Token revoked");
        }

        // 3. check expiration
        if (storedToken.getExpiryDate().before(new Date())) {
            throw new RuntimeException("Token expired");
        }

        // 4. extract user
        String email = jwtService.extractUsername(refreshToken);
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        CustomUserDetails userDetails = new CustomUserDetails(user);

        // 5. generate new tokens
        String newAccessToken = jwtService.generateAccessToken(userDetails);
        String newRefreshToken = jwtService.generateRefreshToken(userDetails);

        // 6. revoke old refresh token
        storedToken.setRevoked(true);
        refreshTokenRepository.save(storedToken);

        // 7. save new refresh token
        saveRefreshToken(newRefreshToken, email);

        // 8. return
        return AuthDtoResponse.builder()
                .accessToken(newAccessToken)
                .refreshToken(newRefreshToken)
                .user(userMapper.toDto(user))
                .build();
    }
}