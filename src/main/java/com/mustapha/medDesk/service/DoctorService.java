package com.mustapha.medDesk.service;

import com.mustapha.medDesk.dto.request.doctor.DoctorDtoRequest;
import com.mustapha.medDesk.dto.response.doctor.DoctorDtoResponse;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public interface DoctorService {
    DoctorDtoResponse create(DoctorDtoRequest dto);
    DoctorDtoResponse update(Long id, DoctorDtoRequest dto);
    DoctorDtoResponse findById(Long id);
    Page<DoctorDtoResponse>getAll(int page, int size);
    void delete(Long id);


}
