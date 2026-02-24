package com.mustapha.medDesk.mapper;

import com.mustapha.medDesk.dto.request.patient.PatientDtoRequest;
import com.mustapha.medDesk.dto.response.patient.PatientDtoResponse;
import com.mustapha.medDesk.model.Patient;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface PatientMapper {
    @Mapping(source = "appointments", target = "appointmentsIds")
    PatientDtoResponse toDto(Patient patient);

    Patient toEntity(PatientDtoRequest dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updatePatientForm(PatientDtoRequest request, @MappingTarget Patient patient);
}
