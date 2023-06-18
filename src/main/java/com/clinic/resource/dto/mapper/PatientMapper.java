package com.clinic.resource.dto.mapper;

import com.clinic.resource.model.Patient;
import com.clinic.resource.dto.PatientDto;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface PatientMapper {
    Patient patientDtoToPatient(PatientDto patientDto);

    PatientDto patientToPatientDto(Patient patient);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updatePatientFromPatientDto(PatientDto patientDto, @MappingTarget Patient patient);
}
