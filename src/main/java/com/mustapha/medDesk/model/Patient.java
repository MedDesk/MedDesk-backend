package com.mustapha.medDesk.model;

import com.mustapha.medDesk.enums.MaritalStatus;
import com.mustapha.medDesk.enums.PatientType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "patients")
@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Patient extends User{

    @Column(name = "register_date", nullable = true)
    private LocalDate registerDate;

    @Column(name = "patient_type", nullable = true)
    private PatientType patientType;

    @Column(name = "cnss", nullable = true)
    private String cnss;

    @Column(name = "marital_status", nullable = true)
    private MaritalStatus maritalStatus;


    // each patient has a list of appointments
    @OneToMany(mappedBy = "patient")
    private List<Appointment> appointments;
}
