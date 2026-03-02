package com.mustapha.medDesk.mapper;

import com.mustapha.medDesk.dto.request.staff.StaffDtoRequest;
import com.mustapha.medDesk.dto.response.Staff.StaffDtoResponse;
import com.mustapha.medDesk.model.Staff;
import org.mapstruct.*;

@Mapper(componentModel = "spring",     builder = @Builder(disableBuilder = true) // This disables the use of Lombok Builders
)
public interface StaffMapper {

    @Mappings({
            @Mapping(source = "staffSpecialist", target = "specialist"),
            @Mapping(source = "CIN", target = "cin"),
            @Mapping(source = "id", target = "id") ,// This works because Entity HAS an id
            @Mapping(source = "role", target = "role")
    })
    StaffDtoResponse toDto(Staff dto);


    @Mappings({
            @Mapping(source = "specialist", target = "staffSpecialist"),
            @Mapping(source = "cin", target = "CIN"),
            @Mapping(target = "id", ignore = true), // Explicitly ignore ID because we don't have id in our entity because we extend it form user
            @Mapping(target = "cabinetId", ignore = true),
            @Mapping(source = "role", target = "role"),
    })
    Staff toEntity(StaffDtoRequest request);


    // this is responsible for update staff data with just updated data
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateStaffFromDto(StaffDtoRequest dto, @MappingTarget Staff staff);
}
