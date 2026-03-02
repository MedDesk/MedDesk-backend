package com.mustapha.medDesk.mapper;

import com.mustapha.medDesk.dto.request.MedicalRecord.MedicalRecordDtoRequest;
import com.mustapha.medDesk.dto.response.MedicalRecord.MedicalRecordDtoResponse;
import com.mustapha.medDesk.model.MedicalRecord;
import org.mapstruct.*;


@Mapper(componentModel = "spring")
public interface MedicalRecordMapper {

    @Mapping(source = "staff.id", target = "nurseId")
    @Mapping(source = "doctor.id", target = "doctorId")
    @Mapping(source = "appointment.id", target = "appointmentId")
    @Mapping(source = "vital.id", target = "vitalId")
    MedicalRecordDtoResponse toDto(MedicalRecord medicalRecord);

    @Mapping(source = "nurseId", target = "nurse.id")
    @Mapping(source = "doctorId", target = "doctor.id")
    @Mapping(source = "appointmentId", target = "appointment.id")
    @Mapping(source = "vitalId", target = "vital.id")
    MedicalRecord toEntity(MedicalRecordDtoRequest dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(source = "nurseId", target = "nurse.id")
    @Mapping(source = "doctorId", target = "doctor.id")
    @Mapping(source = "appointmentId", target = "appointment.id")
    @Mapping(source = "vitalId", target = "vital.id")
    void updateMedicalRecordForm(MedicalRecordDtoRequest request, @MappingTarget MedicalRecord medicalRecord);
}