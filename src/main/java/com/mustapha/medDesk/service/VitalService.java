package com.mustapha.medDesk.service;


import com.mustapha.medDesk.dto.request.Vital.VitalDtoRequest;
import com.mustapha.medDesk.dto.response.vital.VitalDtoResponse;

import java.util.List;

public interface VitalService {
    VitalDtoResponse create(VitalDtoRequest dto);
    VitalDtoResponse update(Long id,VitalDtoRequest dto);
    List<VitalDtoResponse> getAll();
    VitalDtoResponse getById(Long id);
    void delete(Long id);
}
