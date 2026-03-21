package com.mustapha.medDesk.service;

import com.mustapha.medDesk.dto.request.auth.register.RegisterDtoRequest;
import com.mustapha.medDesk.dto.request.auth.signup.LoginDtoRequest;
import com.mustapha.medDesk.dto.response.auth.AuthDtoResponse;

public interface AuthService {
    AuthDtoResponse register(RegisterDtoRequest dto);
    AuthDtoResponse Login(LoginDtoRequest dto);
    AuthDtoResponse refreshToken(String refreshToken);
}
