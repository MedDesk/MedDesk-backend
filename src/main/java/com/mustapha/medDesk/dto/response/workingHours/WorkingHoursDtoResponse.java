package com.mustapha.medDesk.dto.response.workingHours;

import com.mustapha.medDesk.enums.DayOfWeek;
import lombok.*;

import java.time.LocalTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WorkingHoursDtoResponse {

    private Long id;
    private DayOfWeek dayOfWeek;
    private LocalTime startTime;
    private LocalTime endTime;
    private boolean active;

}
