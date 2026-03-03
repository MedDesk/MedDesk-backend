package com.mustapha.medDesk.service.impl;

import com.mustapha.medDesk.dto.response.dashboard.DashboardStatisticsResponse;
import com.mustapha.medDesk.enums.AppointmentStatus;

import com.mustapha.medDesk.repository.AppointmentReposiotry;
import com.mustapha.medDesk.repository.DoctorRepository;
import com.mustapha.medDesk.repository.PatientRepository;
import com.mustapha.medDesk.repository.UserRepository;
import com.mustapha.medDesk.service.DashbaordService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DashboardServiceImpl implements DashbaordService {

    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;
    private final AppointmentReposiotry appointmentRepository;
    private  final UserRepository userRepository;

    @Override
    public DashboardStatisticsResponse getStatistics() {

        long numberOfDoctors = doctorRepository.count();
        long numberOfPatients = patientRepository.count();
        long numberOfAppointments = appointmentRepository.count();
        long numberOfUsers = userRepository.count();
//        long numberOfCompletedAppointments =
//                appointmentRepository.countByStatus(AppointmentStatus.COMPLETED);
//        long numberOfCancelledAppointments =
//                appointmentRepository.countByStatus(AppointmentStatus.CANCELLED);

        return new DashboardStatisticsResponse(
                numberOfDoctors,
                numberOfPatients,
                numberOfAppointments,
                numberOfUsers
//                numberOfCompletedAppointments,
//                numberOfCancelledAppointments
        );
    }
}
