package com.mustapha.medDesk.dto.response.Appointment;

import lombok.*;
import java.time.LocalTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AppointmentSlotResponse {
    private LocalTime startTime;
    private LocalTime endTime;
    private boolean isAvailable;
    private Long appointmentId;
    private String status;
}