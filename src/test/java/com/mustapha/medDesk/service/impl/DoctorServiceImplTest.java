package com.mustapha.medDesk.service.impl;

import com.mustapha.medDesk.dto.request.doctor.DoctorDtoRequest;
import com.mustapha.medDesk.dto.response.doctor.DoctorDtoResponse;
import com.mustapha.medDesk.enums.UserRole;
import com.mustapha.medDesk.exception.ResourceNotFoundException;
import com.mustapha.medDesk.exception.ValidationException;
import com.mustapha.medDesk.mapper.DoctorMapper;
import com.mustapha.medDesk.model.Doctor;
import com.mustapha.medDesk.repository.DoctorRepository;
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
class DoctorServiceImplTest {

    @Mock
    private DoctorRepository doctorRepository;

    @Mock
    private DoctorMapper doctorMapper;

    @InjectMocks
    private DoctorServiceImpl doctorService;

    private DoctorDtoRequest doctorDtoRequest;
    private Doctor doctor;
    private DoctorDtoResponse doctorDtoResponse;

    @BeforeEach
    void setUp() {
        // prepate data
        doctorDtoRequest = new DoctorDtoRequest();
        doctorDtoRequest.setFirstName("Mustapha");
        doctorDtoRequest.setLastName("Moutaki");
        doctorDtoRequest.setEmail("mustapha@med.com");
        doctorDtoRequest.setPassword("password");

        doctor = new Doctor();
        doctor.setId(1L);
        doctor.setFirstName("Mustapha");
        doctor.setLastName("Moutaki");
        doctor.setEmail("mustapha@med.com");
        doctor.setRole(UserRole.DOCTOR);

        doctorDtoResponse = new DoctorDtoResponse();
        doctorDtoResponse.setId(1L);
        doctorDtoResponse.setFirstName("Mustapha");
        doctorDtoResponse.setLastName("Moutaki");
        doctorDtoResponse.setEmail("mustapha@med.com");
    }

    // test creaste doctr
    @Test
    void create_ShouldWork_WhenEmailNotExist() {
        when(doctorRepository.findByEmail(any())).thenReturn(Optional.empty());
        when(doctorMapper.toEntity(any())).thenReturn(doctor);
        when(doctorRepository.save(any())).thenReturn(doctor);
        when(doctorMapper.toDto(any())).thenReturn(doctorDtoResponse);

        DoctorDtoResponse response = doctorService.create(doctorDtoRequest);

        assertNotNull(response);
        assertEquals("Mustapha", response.getFirstName());
        verify(doctorRepository, times(1)).save(any());
    }

    @Test
    void create_ShouldThrow_WhenEmailExist() {
        when(doctorRepository.findByEmail(any())).thenReturn(Optional.of(doctor));

        assertThrows(ValidationException.class, () -> doctorService.create(doctorDtoRequest));
    }

    // test update
    @Test
    void update_ShouldWork_WhenDoctorExists() {
        when(doctorRepository.findById(1L)).thenReturn(Optional.of(doctor));
        doNothing().when(doctorMapper).updateDoctorForm(any(), any());
        when(doctorRepository.save(any())).thenReturn(doctor);
        when(doctorMapper.toDto(any())).thenReturn(doctorDtoResponse);

        DoctorDtoResponse response = doctorService.update(1L, doctorDtoRequest);

        assertNotNull(response);
        verify(doctorRepository, times(1)).save(any());
    }

    @Test
    void update_ShouldThrow_WhenDoctorNotFound() {
        when(doctorRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> doctorService.update(1L, doctorDtoRequest));
    }

    // test find by id
    @Test
    void findById_ShouldReturnDoctor_WhenExists() {
        when(doctorRepository.findById(1L)).thenReturn(Optional.of(doctor));
        when(doctorMapper.toDto(any())).thenReturn(doctorDtoResponse);

        DoctorDtoResponse response = doctorService.findById(1L);

        assertNotNull(response);
        assertEquals("Mustapha", response.getFirstName());
    }

    @Test
    void findById_ShouldThrow_WhenNotFound() {
        when(doctorRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> doctorService.findById(1L));
    }

    // get all tst
    @Test
    void getAll_ShouldReturnPage() {
        Page<Doctor> page = new PageImpl<>(List.of(doctor));
        when(doctorRepository.findAll(PageRequest.of(0, 10))).thenReturn(page);
        when(doctorMapper.toDto(any())).thenReturn(doctorDtoResponse);

        Page<DoctorDtoResponse> result = doctorService.getAll(0, 10);

        assertEquals(1, result.getTotalElements());
        assertEquals("Mustapha", result.getContent().get(0).getFirstName());
    }

    // delete test
    @Test
    void delete_ShouldWork_WhenDoctorExists() {
        when(doctorRepository.findById(1L)).thenReturn(Optional.of(doctor));
        doNothing().when(doctorRepository).delete(any());

        doctorService.delete(1L);

        verify(doctorRepository, times(1)).delete(any());
    }

    @Test
    void delete_ShouldThrow_WhenDoctorNotFound() {
        when(doctorRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ValidationException.class, () -> doctorService.delete(1L));
    }
}