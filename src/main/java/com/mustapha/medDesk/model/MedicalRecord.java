package com.mustapha.medDesk.model;

import com.mustapha.medDesk.model.Appointment;
import com.mustapha.medDesk.model.BaseEntity;
import com.mustapha.medDesk.model.Doctor;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "medicalRecords")
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MedicalRecord extends BaseEntity {

    // we can have to add patient but we  don't have too becuse we have aleady the relationship between the appointmet that include the patient

    /*
    @OneToMany
    @JoinColumn(name = "nurse_id", nullable = false)
    private Nurse nurse;

    @OneToMany(mappedBy = "nurse")
private List<MedicalRecord> medicalRecords;

*/
    // doctor
    @ManyToOne
    @JoinColumn(name = "doctor_id", nullable = false)
    private Doctor doctor;

    @OneToOne
    @JoinColumn(name = "appointment_id", nullable = false, unique = true)
    private Appointment appointment;

    /*
    so in the vitals we have to put the id of the medicalRecord
     */
//    @OneToOne(mappedBy = "medicalRecord")
//    private Vital vital;
}
