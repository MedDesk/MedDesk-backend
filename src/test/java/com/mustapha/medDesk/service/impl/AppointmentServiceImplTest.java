package com.mustapha.medDesk.service.impl;

import com.mustapha.medDesk.dto.request.appointment.AppointmentDtoRequest;
import com.mustapha.medDesk.dto.response.Appointment.AppointmentDtoResponse;
import com.mustapha.medDesk.enums.AppointmentStatus;
import com.mustapha.medDesk.enums.DayOfWeek;
import com.mustapha.medDesk.exception.ValidationException;
import com.mustapha.medDesk.mapper.AppointmentMapper;
import com.mustapha.medDesk.model.Appointment;
import com.mustapha.medDesk.model.Patient;
import com.mustapha.medDesk.model.WorkingHours;
import com.mustapha.medDesk.repository.AppointmentReposiotry;
import com.mustapha.medDesk.repository.PatientRepository;
import com.mustapha.medDesk.repository.WorkingHoursRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AppointmentServiceImplTest {

    @Mock
    private AppointmentReposiotry appointmentReposiotry;
    @Mock
    private WorkingHoursRepository workingHoursRepository;
    @Mock
    private PatientRepository patientRepository;
    @Mock
    private AppointmentMapper appointmentMapper;

    @InjectMocks
    private AppointmentServiceImpl appointmentService;

    private AppointmentDtoRequest request;
    private WorkingHours workingHours;

    @BeforeEach
    void setUp() {
        /**
         * here we prepare the data for every test
         */
        request = new AppointmentDtoRequest();
        // this is a monday
        request.setAppointmentDate(LocalDate.of(2025, 2, 24));
        request.setScheduleTimeStart(LocalTime.of(10, 0));
        request.setPatientId(1L);
        request.setAppointmentStatus(AppointmentStatus.SCHEDULED);

        workingHours = new WorkingHours();
        workingHours.setDayOfWeek(DayOfWeek.MONDAY);
        workingHours.setStartTime(LocalTime.of(9, 0));
        workingHours.setEndTime(LocalTime.of(17, 0));
    }

    @Test
    void create_ShouldWork_WhenEverythingIsOk() {
        /**
         * here we mock te repository to return what we want
         */
        when(workingHoursRepository.findByDayOfWeek(any())).thenReturn(Optional.of(workingHours));

        // mock dabatabse to say this slot is free (false means not exists)
        when(appointmentReposiotry.existsByAppointmentDateAndScheduleTimeStartAndScheduleTimeEnd(any(), any(), any()))
                .thenReturn(false);

        // mock patient exist in db
        when(patientRepository.findById(1L)).thenReturn(Optional.of(new Patient()));

        // mock the save and the mapper
        Appointment savedApp = new Appointment();
        when(appointmentReposiotry.save(any())).thenReturn(savedApp);
        when(appointmentMapper.toDto(any())).thenReturn(new AppointmentDtoResponse());

        /**
         * we call the method we wanna test
         */
        AppointmentDtoResponse response = appointmentService.create(request);

        /**
         * we check if te result is not null and save was called
         */
        assertNotNull(response);
        verify(appointmentReposiotry, times(1)).save(any());
    }

    @Test
    void create_ShouldFail_WhenNoWorkingHours() {
        /**
         * here we mock te repo to return empty so it throws exception
         */
        when(workingHoursRepository.findByDayOfWeek(any())).thenReturn(Optional.empty());

        /**
         * we check if te service throw ValidationException
         */
        assertThrows(ValidationException.class, () -> appointmentService.create(request));
    }

    @Test
    void delete_ShouldThrowError_WhenIdNotExist() {
        /**
         * we mock dabatabse to say id not exist
         */
        when(appointmentReposiotry.existsById(99L)).thenReturn(false);

        /**
         * we verify if it throw the exception
         */
        assertThrows(RuntimeException.class, () -> appointmentService.delete(99L));
    }
}