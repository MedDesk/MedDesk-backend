package com.mustapha.medDesk.dto.response.MedicalRecord;

import com.mustapha.medDesk.model.Doctor;
import com.mustapha.medDesk.model.Staff;
import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MedicalRecordDtoResponse {

    private Long id;
    private Long nurseId;
    private Long doctorId;
    private Long appointmentId;
    private Long vitalId;

}
