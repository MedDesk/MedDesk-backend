package com.mustapha.medDesk.dto.request.user;

import com.mustapha.medDesk.enums.Gender;
import com.mustapha.medDesk.enums.UserRole;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;

@Data
public class UserDtoRequest {
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

        @NotNull(message = "Gender is required") // Add this!
        private Gender gender;
        private LocalDate birthDate;
        private String address;
        private String cin;

        @NotNull(message = "Role is required")
        private UserRole role;



}
