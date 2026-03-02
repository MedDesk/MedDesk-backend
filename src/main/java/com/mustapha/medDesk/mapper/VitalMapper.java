package com.mustapha.medDesk.mapper;

import com.mustapha.medDesk.dto.request.Vital.VitalDtoRequest;
import com.mustapha.medDesk.dto.response.vital.VitalDtoResponse;
import com.mustapha.medDesk.model.Vital;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface VitalMapper {



    @Mapping(source = "medicalRecord.id", target = "medicalRecordId")
    VitalDtoResponse toDto(Vital vital);


    @Mapping(target = "medicalRecord", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "updatedDate", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    Vital toEntity(VitalDtoRequest dto);


    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "medicalRecord", ignore = true)
    void updateVitalFromDto(VitalDtoRequest dto, @MappingTarget Vital vital);
}
