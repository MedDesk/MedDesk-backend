package com.mustapha.medDesk.dto.request.staff;


import com.mustapha.medDesk.enums.*;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StaffDtoRequest {


    // Normal user informations
    @NotBlank(message = "first name is required")
    private String firstName;
    @NotBlank(message = "last name is required")
    private String lastName;
    @NotBlank(message = "username is required")
    private String username;
    @Email
    private String email;

    @NotBlank(message = "password must not be empty")
    @Size(min = 8, message = "Password must be at least 8 characters")
    private String password;

    @Nullable
    private String phone;
    private String profileImage;

    @NotNull(message = "Gender is required")
    private Gender gender;
    private LocalDate birthDate;
    private String address;
    private String cin;

    // staff information
    @NotBlank(message = "the specialist is required")
    private StaffSpecialist specialist;

    @NotNull(message = "shiftPreference is required")
    private ShiftPreference shiftPreference;

    @NotNull(message = "Employment status is required")
    private EmploymentStatus employmentStatus;

    private ContractType contractType;
    private StaffType staffType;


}
