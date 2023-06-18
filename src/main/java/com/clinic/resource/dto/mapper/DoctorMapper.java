package com.clinic.resource.dto.mapper;

import com.clinic.resource.model.Doctor;
import com.clinic.resource.dto.DoctorDto;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface DoctorMapper {

    Doctor doctorDtoToDoctor(DoctorDto doctorDto);

    DoctorDto doctorToDoctorDto(Doctor doctor);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateDoctorFromDoctorDto(DoctorDto doctorDto, @MappingTarget Doctor doctor);
}
