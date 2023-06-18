package com.clinic.resource.service;

import com.clinic.resource.dto.AppointmentDto;
import com.clinic.resource.dto.DoctorDto;

import java.util.List;
import java.util.UUID;

public interface DoctorService {

    List<DoctorDto> getAllDoctors();

    DoctorDto getDoctorById(UUID doctorId);

    DoctorDto createDoctor(DoctorDto doctorDto);

    DoctorDto updateDoctorById(DoctorDto doctorDto, UUID doctorId);

    void deleteDoctorById(UUID doctorId);

    List<AppointmentDto> getDoctorFreeAppointments(UUID doctorId);

    List<AppointmentDto> getDoctorAppointments(UUID doctorId);

    AppointmentDto createAppointment(UUID doctorId, AppointmentDto appointmentDto);
}


