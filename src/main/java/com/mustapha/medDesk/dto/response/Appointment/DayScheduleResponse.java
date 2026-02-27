package com.mustapha.medDesk.dto.response.Appointment;

import com.mustapha.medDesk.enums.DayOfWeek;
import lombok.*;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DayScheduleResponse {
    private DayOfWeek dayOfWeek;
    private LocalDate date;
    private List<AppointmentSlotResponse> slots;
}

/**
 * this is responsable for retunr all days and appointments available and unVailable
 */