package com.mustapha.medDesk.dto.response.vital;

import com.mustapha.medDesk.enums.BloodGroup;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VitalDtoResponse {

    private Long id;
    private Long medicalRecordId;
    private Integer systolicBP;
    private Integer diastolicBP;
    private Integer heartRate;
    private Integer respirationRate;
    private Integer spo2;
    private Double temperature;
    private Double weight;
    private Double height;
    private Double bmi;
    private BloodGroup bloodGroup;
    private String ambulationHistory;
    private Boolean hasFeverHistory;
    private Double bloodSugar;
    private LocalDateTime recordedAt;
}
