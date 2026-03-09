package com.mustapha.medDesk.dto.response.dashboard;

import lombok.Builder;
import lombok.Data;

@Data @Builder
public class RecentAppointmentDto {
    private Long id;
    private String patientName;
    private String time;
    private String status;
}
