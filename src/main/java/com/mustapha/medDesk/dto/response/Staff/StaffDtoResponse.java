package com.mustapha.medDesk.dto.response.Staff;


import com.mustapha.medDesk.enums.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StaffDtoResponse {

    // user data
    private Long id;
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    //    private String password;
    private String phone;
    private String profileImage;
    private Gender gender;
    private LocalDate birthDate;
    private String address;
    private String cin;
    private UserRole role;

    // staff data
    private StaffSpecialist specialist;
    private ShiftPreference shiftPreference;
    private EmploymentStatus employmentStatus;
    private ContractType contractType;
    private StaffType staffType;
}
