package com.mustapha.medDesk.mapper;

import com.mustapha.medDesk.dto.request.doctor.DoctorDtoRequest;
import com.mustapha.medDesk.dto.response.doctor.DoctorDtoResponse;
import com.mustapha.medDesk.model.Doctor;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface DoctorMapper {

    @Mappings({
            @Mapping(target = "medicalRecordIds", ignore = true),
            @Mapping(source = "emergencyContact", target = "emergency_contact"),
            @Mapping(source = "licenseNumber", target = "license_number"),
            @Mapping(source = "CIN", target = "CIN") // <--- تم التغيير: المصدر في الـ Entity هو CIN بحروف كبيرة
    })
    DoctorDtoResponse toDto(Doctor doctor);

    @Mappings({
            @Mapping(source = "emergency_contact", target = "emergencyContact"),
            @Mapping(source = "license_number", target = "licenseNumber"),
            @Mapping(source = "cin", target = "CIN"),
            @Mapping(target = "medicalRecords", ignore = true)
    })
    Doctor toEntity(DoctorDtoRequest dtoRequest);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "medicalRecords", ignore = true)
    @Mapping(source = "emergency_contact", target = "emergencyContact")
    @Mapping(source = "license_number", target = "licenseNumber")
    @Mapping(source = "cin", target = "CIN")
    void updateDoctorForm(DoctorDtoRequest dto, @MappingTarget Doctor doctor);
}