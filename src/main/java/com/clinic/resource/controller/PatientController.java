package com.clinic.resource.controller;

import com.clinic.resource.dto.AppointmentDto;
import com.clinic.resource.dto.PatientDto;
import com.clinic.resource.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/patients")
@RequiredArgsConstructor
@CrossOrigin
public class PatientController {

    private final PatientService patientService;

    @GetMapping
    public List<PatientDto> getAllPatients() {
        return patientService.getAllPatients();
    }

    @GetMapping("/{patientId}")
    public PatientDto getPatientById(@PathVariable UUID patientId) {
        return patientService.getPatientById(patientId);
    }

    @PostMapping
    public ResponseEntity<PatientDto> createPatient(@RequestBody @Valid PatientDto patientDto) {
        PatientDto savedPatient = patientService.createPatient(patientDto);
        return new ResponseEntity<>(savedPatient, HttpStatus.CREATED);
    }

    @PutMapping("/{patientId}")
    public ResponseEntity<PatientDto> updatePatient(@RequestBody @Valid PatientDto patientDto, @PathVariable UUID patientId) {
        PatientDto savedPatient = patientService.updatePatient(patientDto, patientId);
        return new ResponseEntity<>(savedPatient, HttpStatus.OK);
    }

    @DeleteMapping("/{patientId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePatientById(@PathVariable UUID patientId) {
        patientService.deletePatientById(patientId);
    }

    @GetMapping("/{patientId}/appointments")
    public List<AppointmentDto> getAllPatientAppointments(@PathVariable UUID patientId) {
        return patientService.getAllPatientAppointments(patientId);
    }

    @PostMapping("/{patientId}/appointments/{appId}")
    public ResponseEntity<AppointmentDto> makeAppointment(@PathVariable UUID patientId, @PathVariable UUID appId) {
        AppointmentDto savedAppointment = patientService.makeAppointment(patientId, appId);
        return new ResponseEntity<>(savedAppointment, HttpStatus.CREATED);
    }

    @PutMapping("/{patientId}/appointments/{appId}/cancel")
    public ResponseEntity<AppointmentDto> cancelAppointment(@PathVariable UUID patientId, @PathVariable UUID appId) {
        AppointmentDto updatedAppointment = patientService.cancelAppointment(patientId, appId);
        return new ResponseEntity<>(updatedAppointment, HttpStatus.OK);
    }
}
