package com.mustapha.medDesk.dto.response.dashboard;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DashboardStatisticsResponse {

    private long numberOfDoctors;
    private long numberOfPatients;
    private long numberOfStaff;
    private long numberOfMedicalRecords;
    private long numberOfAppointments;
    private long numberOfCompletedAppointments;
    private long numberOfCancelledAppointments;
    private long numberOfUsers;

    public DashboardStatisticsResponse(long numberOfDoctors, long numberOfPatients, long numberOfAppointments, long numberOfUsers) {
        this(
                numberOfDoctors,
                numberOfPatients,
                0,
                0,
                numberOfAppointments,
                0,
                0,
                numberOfUsers
        );
    }
}
