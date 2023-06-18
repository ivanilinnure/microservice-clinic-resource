package com.clinic.resource.dto.mapper;

import com.clinic.resource.model.Appointment;
import com.clinic.resource.dto.AppointmentDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface AppointmentMapper {
    Appointment appointmentDtoToAppointment(AppointmentDto appointmentDto);

    AppointmentDto appointmentToAppointmentDto(Appointment appointment);
}
