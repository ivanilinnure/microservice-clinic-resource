package com.clinic.resource.dto.mapper;

import com.clinic.resource.model.Specialization;
import com.clinic.resource.dto.SpecializationDto;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface SpecializationMapper {

    Specialization specDtoToSpec(SpecializationDto specializationDto);

    SpecializationDto specToSpecDto(Specialization specialization);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateSpecializationFromSpecializationDto(SpecializationDto specializationDto, @MappingTarget Specialization specialization);
}

