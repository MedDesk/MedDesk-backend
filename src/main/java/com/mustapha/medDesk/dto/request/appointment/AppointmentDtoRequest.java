package com.mustapha.medDesk.dto.request.appointment;

import com.mustapha.medDesk.enums.AppointmentStatus;
import com.mustapha.medDesk.util.validation.ValidAppointmentTime;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ValidAppointmentTime // it's valid that the date start time is before the end date
public class AppointmentDtoRequest {

    @NotNull(message = "Start time is required")
    @Future
    private LocalTime scheduleTimeStart;

    // it's gonna calculate automatically
    @NotNull(message = "End time is required")
    private LocalTime scheduleTimeEnd;

    @NotNull(message = "Status is required")
    private AppointmentStatus appointmentStatus;

    @NotNull(message = "Please select the patient")
    private Long patientId;


    public void setScheduleTimeStart(LocalTime scheduleTimeStart) {
        this.scheduleTimeStart = scheduleTimeStart;
        if (scheduleTimeStart != null) {
            this.scheduleTimeEnd = scheduleTimeStart.plusMinutes(30);
        }
    }
}