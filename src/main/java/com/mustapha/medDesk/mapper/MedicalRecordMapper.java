package com.mustapha.medDesk.mapper;

import com.mustapha.medDesk.dto.request.MedicalRecord.MedicalRecordDtoRequest;
import com.mustapha.medDesk.dto.response.MedicalRecord.MedicalRecordDtoResponse;
import com.mustapha.medDesk.model.MedicalRecord;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MedicalRecordMapper {

    // Still need this for the Response
    @Mapping(source = "nurse.id", target = "nurseId")
    @Mapping(source = "doctor.id", target = "doctorId")
    @Mapping(source = "appointment.id", target = "appointmentId")
    @Mapping(source = "vital.id", target = "vitalId")
    MedicalRecordDtoResponse toDto(MedicalRecord medicalRecord);

    // We IGNORE the related entities here because we will set them in the Service
    @Mapping(target = "nurse", ignore = true)
    @Mapping(target = "doctor", ignore = true)
    @Mapping(target = "appointment", ignore = true)
    @Mapping(target = "vital", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "updatedDate", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    MedicalRecord toEntity(MedicalRecordDtoRequest dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "nurse", ignore = true)
    @Mapping(target = "doctor", ignore = true)
    @Mapping(target = "appointment", ignore = true)
    @Mapping(target = "vital", ignore = true)
    void updateMedicalRecordForm(MedicalRecordDtoRequest request, @MappingTarget MedicalRecord medicalRecord);


}