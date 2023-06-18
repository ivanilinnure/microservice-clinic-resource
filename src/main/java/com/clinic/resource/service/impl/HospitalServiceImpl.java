package com.clinic.resource.service.impl;

import com.clinic.resource.dto.HospitalDto;
import com.clinic.resource.dto.mapper.HospitalMapper;
import com.clinic.resource.model.Hospital;
import com.clinic.resource.repository.HospitalRepository;
import com.clinic.resource.service.HospitalService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HospitalServiceImpl implements HospitalService {

    private final HospitalMapper hospitalMapper;
    private final HospitalRepository hospitalRepository;

    @Override
    public HospitalDto createHospital(HospitalDto hospitalDto) {
        Hospital hospital = hospitalRepository.save(hospitalMapper.hospitalDtoToHospital(hospitalDto));
        return hospitalMapper.hospitalToHospitalDto(hospital);
    }

    @Override
    public void updateHospital(HospitalDto hospitalDto, UUID hospitalId) {
        Hospital hospital = hospitalRepository.findById(hospitalId).orElseThrow(() -> new EntityNotFoundException("Hospital with such id: " + hospitalId + " not found"));
        hospitalMapper.updateHospitalFromHospitalDto(hospitalDto, hospital);
        hospitalRepository.save(hospital);
    }

    @Override
    public void deleteHospitalById(UUID hospitalId) {
        if (hospitalRepository.existsById(hospitalId)) {
            hospitalRepository.deleteById(hospitalId);
        } else {
            throw new EntityNotFoundException("Hospital with id: " + hospitalId + " does not exist");
        }
    }

    @Override
    public List<HospitalDto> getAllHospitals() {
        return hospitalRepository.findAll().stream().map(hospitalMapper::hospitalToHospitalDto).collect(Collectors.toList());
    }

    @Override
    public HospitalDto getHospitalByName(String hospitalName) {
        return hospitalRepository.findByName(hospitalName).map(hospitalMapper::hospitalToHospitalDto).orElseThrow(() -> new EntityNotFoundException("Hospital with name: " + hospitalName + " not found"));
    }

    @Override
    public HospitalDto getHospitalById(UUID hospitalId) {
        return hospitalRepository.findById(hospitalId).map(hospitalMapper::hospitalToHospitalDto).orElseThrow(() -> new EntityNotFoundException("Hospital with id: " + hospitalId + " not found"));
    }
}
