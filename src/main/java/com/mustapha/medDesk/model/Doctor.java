package com.mustapha.medDesk.model;

import com.mustapha.medDesk.enums.DoctorSpeacialist;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.annotations.CollectionId;

import java.util.List;

@Entity
@Table(name = "doctors")
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Doctor extends User{

    @Column(nullable = false)
    private DoctorSpeacialist specialist;

    @Column(name = "license_number", nullable = true)
    private String licenseNumber;

    @Column(name = "emergency_contact", nullable = true)
    private String emergencyContact;
    // each doctor has a list of medicalRecords that he made
//    @OneToMany()
//    private List<MedicalRecord> medicalRecords;
}
