package com.mustapha.medDesk.service;

import com.mustapha.medDesk.dto.request.appointment.AppointmentDtoRequest;
import com.mustapha.medDesk.dto.response.Appointment.AppointmentDtoResponse;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public interface AppointmentService {
    AppointmentDtoResponse create(AppointmentDtoRequest dto);
    AppointmentDtoResponse update(Long id, AppointmentDtoRequest dto);
    Page<AppointmentDtoResponse>findAll(int page, int size);
    AppointmentDtoResponse getById(Long id);
    void delete(Long id);
}
