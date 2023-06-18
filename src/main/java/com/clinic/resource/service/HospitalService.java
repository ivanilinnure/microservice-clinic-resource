package com.clinic.resource.service;

import com.clinic.resource.dto.HospitalDto;

import java.util.List;
import java.util.UUID;

public interface HospitalService {

    HospitalDto createHospital(HospitalDto hospitalDto);

    void updateHospital(HospitalDto hospitalDto, UUID hospitalId);

    void deleteHospitalById(UUID hospitalId);

    List<HospitalDto> getAllHospitals();

    HospitalDto getHospitalByName(String hospitalName);

    HospitalDto getHospitalById(UUID hospitalId);

}
