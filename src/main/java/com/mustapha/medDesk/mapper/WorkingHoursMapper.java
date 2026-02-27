package com.mustapha.medDesk.mapper;


import com.mustapha.medDesk.dto.request.workingHour.WorkingHoursDtoRequest;
import com.mustapha.medDesk.dto.response.workingHours.WorkingHoursDtoResponse;
import com.mustapha.medDesk.model.WorkingHours;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface WorkingHoursMapper {

    WorkingHoursDtoResponse toDto(WorkingHours workingHours);


    WorkingHours toEntity(WorkingHoursDtoRequest workingHoursDtoRequest);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateWorkingHoursFromDto(WorkingHoursDtoRequest dto, @MappingTarget WorkingHours workingHours);
}
