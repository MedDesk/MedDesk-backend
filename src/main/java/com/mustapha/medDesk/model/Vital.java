package com.mustapha.medDesk.model;

import com.mustapha.medDesk.enums.BloodGroup;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "vitals")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Vital extends BaseEntity{

    // --- Cardiovascular ---
    private Integer systolicBP;  // Use Integer for math (e.g., 120)
    private Integer diastolicBP; // Use Integer for math (e.g., 80)
    private Integer heartRate;   // beats per minute

    // --- Respiratory ---
    private Integer respirationRate; // breaths per minute
    private Integer spo2;            // Oxygen saturation (e.g., 98 for 98%)

    // --- Body Metrics ---
    private Double temperature;      // Changed from Boolean to Double (e.g., 37.5)
    private Double weight;           // Useful to have (kg)
    private Double height;           // Useful to have (cm)
    private Double bmi;              // Body Mass Index (usually a decimal like 22.5)

    @Enumerated(EnumType.STRING)
    private BloodGroup bloodGroup;

    @Column(columnDefinition = "TEXT")
    private String ambulationHistory; // // How the patient moves (walking, wheelchair, etc.)

    private Boolean hasFeverHistory;

    private Double bloodSugar;        // Important for diabetic patients
    private LocalDateTime recordedAt; // Timestamp of when the nurse took the vitals


    @OneToOne
    @JoinColumn(name = "medical_record-Id")
    private MedicalRecord medicalRecord;
}












