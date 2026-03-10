package com.mustapha.medDesk.service;

import com.mustapha.medDesk.dto.request.staff.StaffDtoRequest;
import com.mustapha.medDesk.dto.response.Staff.StaffDtoResponse;
import com.mustapha.medDesk.enums.StaffType;
import com.mustapha.medDesk.model.Staff;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface StaffService {
    StaffDtoResponse create(StaffDtoRequest dto);
    Page<StaffDtoResponse> getAll(int page, int size);
    StaffDtoResponse update (long id, StaffDtoRequest staffDtoRequest);
    StaffDtoResponse getById(Long id);
    void delete(Long id);

   List<StaffDtoResponse> getAllSTaffByType(StaffType staffType);
}
