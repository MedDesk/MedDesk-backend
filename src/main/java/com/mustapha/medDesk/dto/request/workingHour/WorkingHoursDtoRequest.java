package com.mustapha.medDesk.dto.request.workingHour;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.mustapha.medDesk.enums.DayOfWeek;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class WorkingHoursDtoRequest {

    @NotNull(message = "Please choose day")
    private DayOfWeek dayOfWeek;

    @NotNull(message = "Please choose start time")
    @JsonFormat(pattern = "HH:mm")
    private LocalTime startTime;

    @NotNull(message = "Please choose end time")
    @JsonFormat(pattern = "HH:mm")
    private LocalTime endTime;

    @NotNull(message = "Please specify if the day is active")
    private Boolean active;
}