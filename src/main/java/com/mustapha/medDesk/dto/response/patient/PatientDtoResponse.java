package com.mustapha.medDesk.dto.response.patient;

import com.mustapha.medDesk.enums.Gender;
import com.mustapha.medDesk.enums.MaritalStatus;
import com.mustapha.medDesk.enums.PatientType;
import jakarta.validation.constraints.*;
import jakarta.websocket.server.ServerEndpoint;
import lombok.*;

import java.time.LocalDate;

@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PatientDtoResponse {


    // general info
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

    // patient specific info
    private LocalDate registerDate;
    private PatientType patientType;
    private String cnss;
    private MaritalStatus maritalStatus;

    // kep attention we gonna retunr ther id of appointment not the whole appointment object
    // private List<long> appointmentsIds = [];


}
