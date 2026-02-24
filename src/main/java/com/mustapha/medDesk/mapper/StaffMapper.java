package com.mustapha.medDesk.mapper;

import com.mustapha.medDesk.dto.request.staff.StaffDtoRequest;
import com.mustapha.medDesk.dto.response.Staff.StaffDtoResponse;
import com.mustapha.medDesk.model.Staff;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface StaffMapper {

    StaffDtoResponse toDto(Staff dto);
    Staff toEntity(StaffDtoRequest request);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateStaffFromDto(StaffDtoRequest dto, @MappingTarget Staff staff);
}
