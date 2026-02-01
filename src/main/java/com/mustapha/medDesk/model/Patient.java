package com.mustapha.medDesk.model;

import com.mustapha.medDesk.enums.MaritalStatus;
import com.mustapha.medDesk.enums.PatientType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

import java.time.LocalDate;

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
}
