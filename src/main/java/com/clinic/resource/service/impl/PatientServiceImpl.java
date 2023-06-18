package com.clinic.resource.service.impl;

import com.clinic.resource.dto.AppointmentDto;
import com.clinic.resource.dto.PatientDto;
import com.clinic.resource.dto.mapper.AppointmentMapper;
import com.clinic.resource.dto.mapper.PatientMapper;
import com.clinic.resource.model.Appointment;
import com.clinic.resource.model.Patient;
import com.clinic.resource.repository.AppointmentRepository;
import com.clinic.resource.repository.PatientRepository;
import com.clinic.resource.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PatientServiceImpl implements PatientService {

    private final PatientRepository patientRepository;
    private final PatientMapper patientMapper;
    private final AppointmentRepository appointmentRepository;
    private final AppointmentMapper appointmentMapper;

    @Override
    public List<PatientDto> getAllPatients() {
        return patientRepository.findAll().stream()
                .map(patientMapper::patientToPatientDto)
                .collect(Collectors.toList());
    }

    @Override
    public PatientDto getPatientById(UUID patientId) {
        return patientRepository.findById(patientId)
                .map(patientMapper::patientToPatientDto)
                .orElseThrow(
                        () -> new EntityNotFoundException("Unable to find Patient with id " + patientId));
    }

    @Override
    public PatientDto createPatient(PatientDto patientDto) {
        Patient savedPatient = patientRepository.save(patientMapper.patientDtoToPatient(patientDto));
        return patientMapper.patientToPatientDto(savedPatient);
    }

    @Override
    public PatientDto updatePatient(PatientDto patientDto, UUID patientId) {
        Patient patient = patientRepository.findById(patientId).orElseThrow(
                () -> new EntityNotFoundException("Unable to find Patient with id " + patientId));
        patientMapper.updatePatientFromPatientDto(patientDto, patient);
        Patient savedPatient = patientRepository.save(patient);
        return patientMapper.patientToPatientDto(savedPatient);
    }

    @Override
    public void deletePatientById(UUID patientId) {
        if (patientRepository.existsById(patientId)) {
            patientRepository.deleteById(patientId);
        } else {
            throw new EntityNotFoundException("Unable to find Patient with id " + patientId);
        }
    }

    @Override
    public AppointmentDto makeAppointment(UUID patientId, UUID appId) {
        Appointment appointment = appointmentRepository.findAppointmentByIdAndPatientIsNull(appId).orElseThrow(
                () -> new EntityNotFoundException("Unable to find Appointment with id " + appId));
        Patient patient = patientRepository.findById(patientId).orElseThrow(
                () -> new EntityNotFoundException("Unable to find Patient with id " + patientId));
        appointment.setPatient(patient);
        Appointment savedAppointment = appointmentRepository.save(appointment);
        return appointmentMapper.appointmentToAppointmentDto(savedAppointment);
    }

    @Override
    public AppointmentDto cancelAppointment(UUID patientId, UUID appId) {
        Patient patient = patientRepository.findById(patientId).orElseThrow(
                () -> new EntityNotFoundException("Unable to find Patient with id " + patientId));
        Appointment appointment = appointmentRepository.findAppointmentByIdAndPatient(appId, patient).orElseThrow(
                () -> new EntityNotFoundException("Unable to find Appointment with id " + appId + " and patientId " + patientId));
        appointment.setPatient(null);
        Appointment savedAppointment = appointmentRepository.save(appointment);
        return appointmentMapper.appointmentToAppointmentDto(savedAppointment);
    }

    @Override
    public List<AppointmentDto> getAllPatientAppointments(UUID patientId) {
        if (patientRepository.existsById(patientId)) {
            return appointmentRepository.findAppointmentsByPatientId(patientId).stream()
                    .map(appointmentMapper::appointmentToAppointmentDto)
                    .collect(Collectors.toList());
        } else {
            throw new EntityNotFoundException("Unable to find Patient with id " + patientId);
        }
    }
}
