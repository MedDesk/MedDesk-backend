package com.mustapha.medDesk.model;

import com.mustapha.medDesk.enums.AppointmentStatus;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "appointments")
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Appointment extends BaseEntity {

    /*
    The appointment date is required to track which day
    the patient is visiting the clinic.
    */

    @Column(name = "appointment_date", nullable = false)
    private LocalDate appointmentDate;

    @Column(name = "appointment_start_time", nullable = false)
    private LocalTime scheduleTimeStart;

    @Column(name = "appointment_end_time", nullable = false)
    private LocalTime scheduleTimeEnd;

    @Enumerated(EnumType.STRING)
    @Column(name = "appointment_status", nullable = false)
    private AppointmentStatus appointmentStatus = AppointmentStatus.SCHEDULED;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;

    @OneToOne(mappedBy = "appointment")
    private MedicalRecord medicalRecord;
}