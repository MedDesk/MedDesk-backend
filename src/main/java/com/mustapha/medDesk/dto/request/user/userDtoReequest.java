package com.mustapha.medDesk.dto.request.user;

import com.mustapha.medDesk.enums.Gender;
import com.mustapha.medDesk.enums.UserRole;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;

@Data
public class userDtoReequest {
        @NotBlank(message = "first name is required")
        private String firstName;
        @NotBlank(message = "last name is required")
        private String lastName;
        @NotBlank(message = "username is required")
        private String username;
        @Email
        private String email;

        @NotBlank(message = "password must not be empty")
        @Size(min = 4, message = "password must be at least 5 characters")
        private String password;

        @Nullable
        private String phone;
        private String profileImage;

        private Gender gender;
        private LocalDate birthDate;
        private String address;
        private String cin;

        @NotBlank(message = "user role is required")
        private UserRole role;



}
