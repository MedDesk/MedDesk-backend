package com.mustapha.medDesk.service;

import com.mustapha.medDesk.dto.request.appointment.AppointmentDtoRequest;
import com.mustapha.medDesk.dto.response.Appointment.AppointmentDtoResponse;
import com.mustapha.medDesk.dto.response.Appointment.AppointmentSlotResponse;
import com.mustapha.medDesk.dto.response.Appointment.DayScheduleResponse;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public interface AppointmentService {
    AppointmentDtoResponse create(AppointmentDtoRequest dto);
    AppointmentDtoResponse update(Long id, AppointmentDtoRequest dto);
    Page<AppointmentDtoResponse>findAll(int page, int size);
    AppointmentDtoResponse getById(Long id);
    void delete(Long id);

    List<AppointmentSlotResponse> getSlotsByDate(LocalDate date);
    List<DayScheduleResponse>  getWeeklyAvailability(); // return all days and appointments
}
