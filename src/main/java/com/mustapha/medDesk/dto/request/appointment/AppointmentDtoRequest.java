package com.mustapha.medDesk.dto.request.appointment;

import com.mustapha.medDesk.enums.AppointmentStatus;
import com.mustapha.medDesk.util.validation.ValidAppointmentTime;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ValidAppointmentTime // it's valid that the date start time is before the end date
public class AppointmentDtoRequest {

    @NotNull(message = "Start time is required")
    @Future
    private LocalDateTime scheduleTimeStart;

    // it's gonna calculate automatically
//    @NotNull(message = "End time is required")
//    private LocalDateTime scheduleTimeEnd;

    @NotNull(message = "Status is required")
    private AppointmentStatus appointmentStatus;

    @NotNull(message = "Please select the patient")
    private Long patientId;
}