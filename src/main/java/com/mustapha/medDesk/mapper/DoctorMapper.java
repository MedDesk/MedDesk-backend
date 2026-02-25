package com.mustapha.medDesk.mapper;

import com.mustapha.medDesk.dto.request.doctor.DoctorDtoRequest;
import com.mustapha.medDesk.dto.response.doctor.DoctorDtoResponse;
import com.mustapha.medDesk.model.Doctor;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface DoctorMapper {
    @Mapping(source = "medicalRecords", target = "medicalRecordIds")
    DoctorDtoResponse toDto(Doctor dto);


    Doctor toEntity(DoctorDtoRequest dtoRequest);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "password", ignore = true)
    void updateDoctorForm(DoctorDtoRequest dto, @MappingTarget Doctor doctor);
}
