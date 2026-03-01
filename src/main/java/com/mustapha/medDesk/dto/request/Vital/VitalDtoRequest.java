package com.mustapha.medDesk.dto.request.Vital;

import com.mustapha.medDesk.enums.BloodGroup;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VitalDtoRequest {
    @NotNull(message = "Medical Record ID is required")
    private Long medicalRecordId;

    @NotNull(message = "Systolic BP is required")
    @Min(value = 50, message = "Systolic BP must be at least 50")
    @Max(value = 250, message = "Systolic BP cannot exceed 250")
    private Integer systolicBP;

    @NotNull(message = "Diastolic BP is required")
    @Min(value = 30, message = "Diastolic BP must be at least 30")
    @Max(value = 150, message = "Diastolic BP cannot exceed 150")
    private Integer diastolicBP;

    @NotNull(message = "Heart rate is required")
    @Min(value = 20, message = "Heart rate must be at least 20")
    @Max(value = 250, message = "Heart rate cannot exceed 250")
    private Integer heartRate;

    @NotNull(message = "Respiration rate is required")
    @Min(value = 0)
    @Max(value = 100)
    private Integer respirationRate;

    @NotNull(message = "SPO2 (Oxygen) is required")
    @Min(value = 0)
    @Max(value = 100, message = "SPO2 cannot exceed 100%")
    private Integer spo2;

    @NotNull(message = "Temperature is required")
    @Min(value = 30)
    @Max(value = 45)
    private Double temperature;

    @NotNull(message = "Weight is required")
    @Positive(message = "Weight must be positive")
    private Double weight;

    @NotNull(message = "Height is required")
    @Positive(message = "Height must be positive")
    private Double height;

    // BMI can be null in request because we will calculate it in the service
    private Double bmi;

    @NotNull(message = "Blood group is required")
    private BloodGroup bloodGroup;

    @NotBlank(message = "Ambulation history is required")
    private String ambulationHistory;

    @NotNull(message = "Fever history status is required")
    private Boolean hasFeverHistory;

    @NotNull(message = "Blood sugar level is required")
    @Positive
    private Double bloodSugar;

    @NotNull(message = "Recording timestamp is required")
    private LocalDateTime recordedAt;
}
