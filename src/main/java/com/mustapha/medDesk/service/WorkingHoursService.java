package com.mustapha.medDesk.service;

import com.mustapha.medDesk.dto.request.workingHour.WorkingHoursDtoRequest;
import com.mustapha.medDesk.dto.response.workingHours.WorkingHoursDtoResponse;

import java.util.List;

public interface WorkingHoursService {
    WorkingHoursDtoResponse create(WorkingHoursDtoRequest dto);

    WorkingHoursDtoResponse update(Long id, WorkingHoursDtoRequest dto);
    WorkingHoursDtoResponse getById(Long id);
    List<WorkingHoursDtoResponse> getAll();
    void delete(Long id);
}

