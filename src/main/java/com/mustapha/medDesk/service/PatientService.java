package com.mustapha.medDesk.service;

import com.mustapha.medDesk.dto.request.patient.PatientDtoRequest;
import com.mustapha.medDesk.dto.response.patient.PatientDtoResponse;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public interface PatientService {
    PatientDtoResponse create(PatientDtoRequest dto);
    PatientDtoResponse update(Long id, PatientDtoRequest dto);
    PatientDtoResponse getById(Long id);
    Page<PatientDtoResponse>getAll(int page, int size);
    void delete(Long id);
}
