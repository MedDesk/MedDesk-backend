package com.mustapha.medDesk.service;

import com.mustapha.medDesk.dto.request.MedicalRecord.MedicalRecordDtoRequest;
import com.mustapha.medDesk.dto.response.MedicalRecord.MedicalRecordDtoResponse;
import com.mustapha.medDesk.model.MedicalRecord;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public interface MedicalRecordService {

    MedicalRecordDtoResponse create(MedicalRecordDtoRequest dto);
    MedicalRecordDtoResponse update(Long id, MedicalRecordDtoRequest dto);
    Page<MedicalRecordDtoResponse> getAll(int page, int size);
    MedicalRecordDtoResponse getById(Long id);
    void delete(Long id);

}
