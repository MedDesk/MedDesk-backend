package com.mustapha.medDesk.service.impl;

import com.mustapha.medDesk.dto.request.MedicalRecord.MedicalRecordDtoRequest;
import com.mustapha.medDesk.dto.response.MedicalRecord.MedicalRecordDtoResponse;
import com.mustapha.medDesk.enums.StaffType;
import com.mustapha.medDesk.exception.ResourceNotFoundException;
import com.mustapha.medDesk.exception.ValidationException;
import com.mustapha.medDesk.mapper.MedicalRecordMapper;
import com.mustapha.medDesk.model.*;
import com.mustapha.medDesk.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MedicalRecordServiceImplTest {

    @Mock
    private MedicalRecordRepository medicalRecordRepository;
    @Mock
    private MedicalRecordMapper medicalRecordMapper;
    @Mock
    private AppointmentReposiotry appointmentRepository;
    @Mock
    private DoctorRepository doctorRepository;
    @Mock
    private StaffRepository staffRepository;
    @Mock
    private VitalRepository vitalRepository;

    @InjectMocks
    private MedicalRecordServiceImpl medicalRecordService;

    private MedicalRecordDtoRequest dtoRequest;
    private MedicalRecord medicalRecord;
    private MedicalRecordDtoResponse medicalRecordDtoResponse;
    private Doctor doctor;
    private Staff nurse;
    private Appointment appointment;
    private Vital vital;

    @BeforeEach
    void setUp() {
        dtoRequest = new MedicalRecordDtoRequest();
        dtoRequest.setAppointmentId(1L);
        dtoRequest.setDoctorId(1L);
        dtoRequest.setNurseId(1L);
        dtoRequest.setVitalId(1L);

        doctor = new Doctor();
        doctor.setId(1L);

        nurse = new Staff();
        nurse.setId(1L);
        nurse.setStaffType(StaffType.NURSE);

        appointment = new Appointment();
        appointment.setId(1L);

        vital = new Vital();
        vital.setId(1L);

        medicalRecord = new MedicalRecord();
        medicalRecord.setId(1L);
        medicalRecord.setDoctor(doctor);
        medicalRecord.setNurse(nurse);
        medicalRecord.setAppointment(appointment);
        medicalRecord.setVital(vital);

        medicalRecordDtoResponse = new MedicalRecordDtoResponse();
        medicalRecordDtoResponse.setId(1L);
    }

    // ===================== create =====================
    @Test
    void create_ShouldWork_WhenEverythingIsOk() {
        when(medicalRecordRepository.existsByAppointmentId(any())).thenReturn(false);
        when(staffRepository.findById(any())).thenReturn(Optional.of(nurse));
        when(doctorRepository.findById(any())).thenReturn(Optional.of(doctor));
        when(appointmentRepository.findById(any())).thenReturn(Optional.of(appointment));
        when(vitalRepository.findById(any())).thenReturn(Optional.of(vital));
        when(medicalRecordMapper.toEntity(any())).thenReturn(medicalRecord);
        when(medicalRecordMapper.toDto(any())).thenReturn(medicalRecordDtoResponse);
        when(medicalRecordRepository.save(any())).thenReturn(medicalRecord);

        MedicalRecordDtoResponse response = medicalRecordService.create(dtoRequest);

        assertNotNull(response);
        verify(medicalRecordRepository, times(1)).save(any());
    }

    @Test
    void create_ShouldThrow_WhenAppointmentAlreadyExists() {
        when(medicalRecordRepository.existsByAppointmentId(any())).thenReturn(true);

        assertThrows(ValidationException.class, () -> medicalRecordService.create(dtoRequest));
    }

    @Test
    void create_ShouldThrow_WhenNurseNotFound() {
        when(medicalRecordRepository.existsByAppointmentId(any())).thenReturn(false);
        when(staffRepository.findById(any())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> medicalRecordService.create(dtoRequest));
    }

    @Test
    void create_ShouldThrow_WhenStaffNotANurse() {
        Staff notNurse = new Staff();
        notNurse.setId(1L);
        notNurse.setStaffType(StaffType.LAB_TECH);

        when(medicalRecordRepository.existsByAppointmentId(any())).thenReturn(false);
        when(staffRepository.findById(any())).thenReturn(Optional.of(notNurse));

        assertThrows(ValidationException.class, () -> medicalRecordService.create(dtoRequest));
    }

    // ===================== update =====================
    @Test
    void update_ShouldWork_WhenEverythingIsOk() {
        when(medicalRecordRepository.findById(any())).thenReturn(Optional.of(medicalRecord));
        when(appointmentRepository.existsById(any())).thenReturn(true);
        when(doctorRepository.existsById(any())).thenReturn(true);
        when(staffRepository.existsById(any())).thenReturn(true);
        when(medicalRecordMapper.toDto(any())).thenReturn(medicalRecordDtoResponse);
        when(medicalRecordRepository.save(any())).thenReturn(medicalRecord);

        MedicalRecordDtoResponse response = medicalRecordService.update(1L, dtoRequest);

        assertNotNull(response);
        verify(medicalRecordRepository, times(1)).save(any());
    }

    @Test
    void update_ShouldThrow_WhenMedicalRecordNotFound() {
        when(medicalRecordRepository.findById(any())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> medicalRecordService.update(1L, dtoRequest));
    }

    // ===================== getById =====================
    @Test
    void getById_ShouldReturnRecord_WhenExists() {
        when(medicalRecordRepository.findById(any())).thenReturn(Optional.of(medicalRecord));
        when(medicalRecordMapper.toDto(any())).thenReturn(medicalRecordDtoResponse);

        MedicalRecordDtoResponse response = medicalRecordService.getById(1L);

        assertNotNull(response);
        assertEquals(1L, response.getId());
    }

    @Test
    void getById_ShouldThrow_WhenNotFound() {
        when(medicalRecordRepository.findById(any())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> medicalRecordService.getById(1L));
    }

    // ===================== getAll =====================
    @Test
    void getAll_ShouldReturnPage() {
        Page<MedicalRecord> page = new PageImpl<>(List.of(medicalRecord));
        when(medicalRecordRepository.findAll(PageRequest.of(0, 10))).thenReturn(page);
        when(medicalRecordMapper.toDto(any())).thenReturn(medicalRecordDtoResponse);

        Page<MedicalRecordDtoResponse> result = medicalRecordService.getAll(0, 10);

        assertEquals(1, result.getTotalElements());
    }

    // ===================== delete =====================
    @Test
    void delete_ShouldWork_WhenExists() {
        when(medicalRecordRepository.existsById(any())).thenReturn(true);
        doNothing().when(medicalRecordRepository).deleteById(any());

        medicalRecordService.delete(1L);

        verify(medicalRecordRepository, times(1)).deleteById(any());
    }

    @Test
    void delete_ShouldThrow_WhenNotExists() {
        when(medicalRecordRepository.existsById(any())).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> medicalRecordService.delete(1L));
    }

    // ===================== getPatientHistory =====================
    @Test
    void getPatientHistory_ShouldReturnList() {
        when(medicalRecordRepository.findAllByPatientId(any())).thenReturn(List.of(medicalRecord));
        when(medicalRecordMapper.toDto(any())).thenReturn(medicalRecordDtoResponse);

        List<MedicalRecordDtoResponse> history = medicalRecordService.getPatientHistory(1L);

        assertEquals(1, history.size());
        assertEquals(1L, history.get(0).getId());
    }
}