package com.mustapha.medDesk.mapper;

import com.mustapha.medDesk.dto.request.appointment.AppointmentDtoRequest;
import com.mustapha.medDesk.dto.response.Appointment.AppointmentDtoResponse;
import com.mustapha.medDesk.model.Appointment;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface AppointmentMapper {

    // Entity -> DTO
    @Mapping(source = "patient.id", target = "patientId")
    @Mapping(source = "medicalRecord.id", target = "medicalRecordId")
    AppointmentDtoResponse toDto(Appointment entity);

    // DTO -> Entity
    @Mapping(source = "patientId", target = "patient.id")
    @Mapping(target = "medicalRecord", ignore = true) // Records are usually created later
    Appointment toEntity(AppointmentDtoRequest dto);

    // Update existing entity (for PUT requests)
    @Mapping(target = "id", ignore = true)
    @Mapping(source = "patientId", target = "patient.id")
    void updateEntityFromDto(AppointmentDtoRequest dto, @MappingTarget Appointment entity);
}
