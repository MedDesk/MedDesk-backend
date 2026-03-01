package com.mustapha.medDesk.model;

import com.mustapha.medDesk.enums.BloodGroup;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "vitals")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Vital extends BaseEntity {

    // --- Cardiovascular ---
    // Use @Min and @Max for numbers, @Size is only for Strings/Collections
    @Min(value = 50, message = "Systolic BP is too low")
    @Max(value = 250, message = "Systolic BP is too high")
    private Integer systolicBP;

    @Min(value = 30)
    @Max(value = 150)
    private Integer diastolicBP;

    @Min(value = 30)
    @Max(value = 250)
    private Integer heartRate;

    // --- Respiratory ---
    @Min(value = 0)
    @Max(value = 100)
    private Integer respirationRate;

    @Min(value = 0)
    @Max(value = 100, message = "Oxygen saturation cannot exceed 100%")
    private Integer spo2;

    // --- Body Metrics ---
    private Double temperature;
    private Double weight;
    private Double height;
    private Double bmi;

    @Enumerated(EnumType.STRING)
    private BloodGroup bloodGroup;

    @Column(columnDefinition = "TEXT")
    private String ambulationHistory;

    private Boolean hasFeverHistory;

    private Double bloodSugar;
    private LocalDateTime recordedAt;

    @OneToOne
    @JoinColumn(name = "medical_record_id")
    private MedicalRecord medicalRecord;
}

