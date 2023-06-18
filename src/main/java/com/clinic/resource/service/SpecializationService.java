package com.clinic.resource.service;

import com.clinic.resource.dto.SpecializationDto;

import java.util.List;
import java.util.UUID;

public interface SpecializationService {

    List<SpecializationDto> getAllSpecializations();

    SpecializationDto getSpecializationById(UUID specId);

    SpecializationDto getSpecializationByName(String specName);

    SpecializationDto createSpecialization(SpecializationDto specializationDto);

    SpecializationDto updateSpecialization(SpecializationDto specializationDto, UUID specId);

    void deleteSpecialization(UUID specId);

}
