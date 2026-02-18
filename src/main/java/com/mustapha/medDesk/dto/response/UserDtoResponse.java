package com.mustapha.medDesk.dto.response;

import com.mustapha.medDesk.enums.Gender;
import com.mustapha.medDesk.enums.UserRole;
import lombok.Data;

import java.time.LocalDate;

@Data

public class UserDtoResponse {

    private Long id;
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private String password;
    private String phone;
    private String profileImage;
    private Gender gender;
    private LocalDate birthDate;
    private String address;
    private String cin;
    private UserRole role;

}