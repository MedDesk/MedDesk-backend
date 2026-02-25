package com.mustapha.medDesk.service.impl;

import com.mustapha.medDesk.dto.request.patient.PatientDtoRequest;
import com.mustapha.medDesk.dto.response.patient.PatientDtoResponse;
import com.mustapha.medDesk.exception.ResourceNotFoundException;
import com.mustapha.medDesk.exception.ValidationException;
import com.mustapha.medDesk.mapper.PatientMapper;
import com.mustapha.medDesk.model.Patient;
import com.mustapha.medDesk.repository.PatientRepository;
import com.mustapha.medDesk.service.PatientService;
import com.mustapha.medDesk.util.PasswordUtil;
import jakarta.transaction.Transactional;
import lombok.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@Transactional
@RequiredArgsConstructor
@Getter
@Setter
public class PatientServiceImpl implements PatientService {
    private final PatientRepository patientRepository;
    private final PatientMapper patientMapper;
    @Override
    public PatientDtoResponse create(PatientDtoRequest dto) {
        // check if the patient is already exists
        boolean isExists = patientRepository.findByEmail(dto.getEmail()).isPresent();
        if(!isExists){
            throw new  ValidationException("Email already exist");
        }
        Patient patient = patientMapper.toEntity(dto);
        patient.setPassword(PasswordUtil.hash(dto.getPassword()));
        patient.setRegisterDate(LocalDate.now());

        Patient savedPatient = patientRepository.save(patient);
        return  patientMapper.toDto(savedPatient);
    }

    @Override
    public PatientDtoResponse update(Long id, PatientDtoRequest dto) {
        Patient patient = patientRepository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("Patient not found")
        );
        patientMapper.updatePatientForm(dto, patient);
        patientRepository.save(patient);
        return patientMapper.toDto(patient);
    }

    @Override
    public PatientDtoResponse getById(Long id) {
        Patient patient = patientRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Patient not found"));
        return patientMapper.toDto(patient);
    }

    @Override
    public Page<PatientDtoResponse> getAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return patientRepository.findAll(pageable).map(patient -> patientMapper.toDto(patient));
    }

    @Override
    public void delete(Long id) {
        Patient patient = patientRepository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("Patient not found")
                );
        this.patientRepository.delete(patient);
    }
}
