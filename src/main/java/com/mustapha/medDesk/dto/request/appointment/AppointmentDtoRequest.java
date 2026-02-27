package com.mustapha.medDesk.dto.request.appointment;

import com.mustapha.medDesk.enums.AppointmentStatus;
import com.mustapha.medDesk.util.validation.ValidAppointmentTime;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ValidAppointmentTime
public class AppointmentDtoRequest {

    @NotNull(message = "Start time is required")
    private LocalTime scheduleTimeStart;

    private LocalTime scheduleTimeEnd;

    @NotNull(message = "Status is required")
    private AppointmentStatus appointmentStatus;

    @NotNull(message = "Please select the patient")
    private Long patientId;

    @NotNull(message = "Appointment date is required")
    private LocalDate appointmentDate;

    public void setScheduleTimeStart(LocalTime scheduleTimeStart) {

        this.scheduleTimeStart = scheduleTimeStart;
        if (scheduleTimeStart != null) {
            this.scheduleTimeEnd = scheduleTimeStart.plusMinutes(30);
        }
    }
}