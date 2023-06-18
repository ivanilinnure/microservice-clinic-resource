package com.clinic.resource.service.impl;

import com.clinic.resource.dto.AppointmentDto;
import com.clinic.resource.dto.DoctorDto;
import com.clinic.resource.dto.mapper.AppointmentMapper;
import com.clinic.resource.dto.mapper.DoctorMapper;
import com.clinic.resource.model.Appointment;
import com.clinic.resource.model.Doctor;
import com.clinic.resource.repository.AppointmentRepository;
import com.clinic.resource.repository.DoctorRepository;
import com.clinic.resource.service.DoctorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DoctorServiceImpl implements DoctorService {

    private final DoctorRepository doctorRepository;
    private final DoctorMapper doctorMapper;
    private final AppointmentRepository appointmentRepository;
    private final AppointmentMapper appointmentMapper;

    @Override
    public List<DoctorDto> getAllDoctors() {
        return doctorRepository.findAll()
                .stream()
                .map(doctorMapper::doctorToDoctorDto)
                .collect(Collectors.toList());
    }

    @Override
    public DoctorDto getDoctorById(UUID doctorId) {
        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(
                        () -> new EntityNotFoundException("Unable to find Doctor with id " + doctorId));
        return doctorMapper.doctorToDoctorDto(doctor);
    }

    @Override
    public DoctorDto createDoctor(DoctorDto doctorDto) {
        Doctor doctor = doctorRepository.save(doctorMapper.doctorDtoToDoctor(doctorDto));
        return doctorMapper.doctorToDoctorDto(doctor);
    }

    @Override
    public DoctorDto updateDoctorById(DoctorDto doctorDto, UUID doctorId) {
        Doctor doctor = doctorRepository.findById(doctorId).orElseThrow(
                () -> new EntityNotFoundException("Unable to find Doctor with id " + doctorId));
        doctorMapper.updateDoctorFromDoctorDto(doctorDto, doctor);
        doctorRepository.save(doctor);

        return doctorMapper.doctorToDoctorDto(doctor);
    }

    @Override
    public void deleteDoctorById(UUID doctorId) {
        if (doctorRepository.existsById(doctorId)) {
            doctorRepository.deleteById(doctorId);
        } else {
            throw new EntityNotFoundException("Unable to find Doctor with id " + doctorId);
        }
    }

    @Override
    public List<AppointmentDto> getDoctorFreeAppointments(UUID doctorId) {
        if (doctorRepository.existsById(doctorId)) {
            return appointmentRepository.findAppointmentsByDoctorIdAndPatientIsNull(doctorId).stream()
                    .map(appointmentMapper::appointmentToAppointmentDto)
                    .collect(Collectors.toList());
        } else {
            throw new EntityNotFoundException("Unable to find Doctor with id " + doctorId);
        }
    }

    @Override
    public List<AppointmentDto> getDoctorAppointments(UUID doctorId) {
        if (doctorRepository.existsById(doctorId)) {
            return appointmentRepository.findAppointmentsByDoctorIdAndPatientNotNull(doctorId).stream()
                    .map(appointmentMapper::appointmentToAppointmentDto)
                    .collect(Collectors.toList());
        } else {
            throw new EntityNotFoundException("Unable to find Doctor with id " + doctorId);
        }
    }

    @Override
    public AppointmentDto createAppointment(UUID doctorId, AppointmentDto appointmentDto) {
        Doctor doctor = doctorRepository.findById(doctorId).orElseThrow(
                () -> new EntityNotFoundException("Unable to find Doctor with id " + doctorId));
        Appointment appointment = appointmentMapper.appointmentDtoToAppointment(appointmentDto);
        appointment.setDoctor(doctor);
        appointmentRepository.save(appointment);

        return appointmentMapper.appointmentToAppointmentDto(appointment);
    }
}
