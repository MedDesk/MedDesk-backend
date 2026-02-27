package com.mustapha.medDesk.service.impl;

import com.mustapha.medDesk.dto.request.appointment.AppointmentDtoRequest;
import com.mustapha.medDesk.dto.response.Appointment.AppointmentDtoResponse;

import com.mustapha.medDesk.repository.AppointmentReposiotry;

import com.mustapha.medDesk.service.AppointmentService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentReposiotry appointmentReposiotry;

    @Override
    public AppointmentDtoResponse create(AppointmentDtoRequest dto) {

    }

    @Override
    public AppointmentDtoResponse update(Long id, AppointmentDtoRequest dto) {
        return null;
    }

    @Override
    public Page<AppointmentDtoResponse> findAll(int page, int size) {
        return null;
    }

    @Override
    public AppointmentDtoResponse getById(Long id) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }
}
