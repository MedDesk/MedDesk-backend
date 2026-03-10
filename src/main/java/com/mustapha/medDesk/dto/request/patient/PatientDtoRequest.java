package com.mustapha.medDesk.dto.request.patient;


import com.mustapha.medDesk.enums.Gender;
import com.mustapha.medDesk.enums.MaritalStatus;
import com.mustapha.medDesk.enums.PatientType;
import com.mustapha.medDesk.enums.UserRole;
import com.mustapha.medDesk.model.Appointment;
import jakarta.annotation.Nullable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PatientDtoRequest {

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
    private UserRole role = UserRole.PATIENT;
    // patient specific info
    @NotNull(message = "Patient type is required")
    private PatientType patientType;
    private String cnss;
    private MaritalStatus maritalStatus = MaritalStatus.UNASSIGNED;


}
