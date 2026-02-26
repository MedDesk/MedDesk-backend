package com.mustapha.medDesk.dto.response.Appointment;


import com.mustapha.medDesk.enums.AppointmentStatus;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import jakarta.websocket.server.ServerEndpoint;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class AppointmentDtoResponse {

    private Long id;
    private LocalDateTime scheduleTimeStart;
    private LocalDateTime scheduleTimeEnd;
    private AppointmentStatus appointmentStatus;
    private Long patientId;

}
