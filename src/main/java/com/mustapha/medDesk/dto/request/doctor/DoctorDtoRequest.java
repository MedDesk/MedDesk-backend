package com.mustapha.medDesk.dto.request.doctor;

import com.mustapha.medDesk.enums.DoctorSpeacialist;
import com.mustapha.medDesk.enums.Gender;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DoctorDtoRequest {

    // general info
    @NotBlank(message = "First name is required")
    private String firstName;

    @NotBlank(message = "Last name is required")
    private String lastName;

    @NotBlank(message = "Username is required")
    private String username;

    @Email
    private String email;

    @NotBlank(message = "Password must not be empty")
    @Size(min = 8, message = "Password must be at least 8 characters")
    private String password;

    private String phone;
    private String profileImage;

    @NotNull(message = "Gender is required")
    private Gender gender;

    @NotNull(message = "Birth date is required")
    @Past(message = "Birth date must be in the past")
    private LocalDate birthDate;

    private String address;
    private String cin;

    // doctor
    private String emergency_contact;
    private String license_number;
    private DoctorSpeacialist specialist;

}
