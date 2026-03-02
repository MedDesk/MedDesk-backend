package com.mustapha.medDesk.dto.response.Appointment;


import com.mustapha.medDesk.enums.AppointmentStatus;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import jakarta.websocket.server.ServerEndpoint;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentDtoResponse {

    private Long id;
    private LocalTime scheduleTimeStart;
    private LocalTime scheduleTimeEnd;
    private AppointmentStatus appointmentStatus;
    private Long patientId;
    private Long medicalRecordId;

    private LocalDate appointmentDate;
}
