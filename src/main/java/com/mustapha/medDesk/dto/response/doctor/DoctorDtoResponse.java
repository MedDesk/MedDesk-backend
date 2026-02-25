package com.mustapha.medDesk.dto.response.doctor;


import com.mustapha.medDesk.enums.DoctorSpeacialist;
import com.mustapha.medDesk.enums.Gender;
import com.mustapha.medDesk.model.MedicalRecord;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DoctorDtoResponse {

    private Long id;
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private String phone;
    private String profileImage;
    private Gender gender;
    private LocalDate birthDate;
    private String address;
    private String CIN;

    // doctor data
    private String emergency_contact;
    private String license_number;
    private DoctorSpeacialist specialist;
    private List<Long> medicalRecordIds;
}
