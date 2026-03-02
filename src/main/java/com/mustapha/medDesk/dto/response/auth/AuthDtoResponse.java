package com.mustapha.medDesk.dto.response.auth;

import com.mustapha.medDesk.dto.response.user.UserDtoResponse;
import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuthDtoResponse {
    private String accessToken;
    private String refreshToken;
    private UserDtoResponse user;
}
