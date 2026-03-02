package com.mustapha.medDesk.service.impl;

import com.mustapha.medDesk.dto.request.Vital.VitalDtoRequest;

import com.mustapha.medDesk.dto.response.vital.VitalDtoResponse;
import com.mustapha.medDesk.exception.ResourceNotFoundException;
import com.mustapha.medDesk.mapper.VitalMapper;
import com.mustapha.medDesk.model.MedicalRecord;
import com.mustapha.medDesk.model.Vital;
import com.mustapha.medDesk.repository.MedicalRecordRepository;
import com.mustapha.medDesk.repository.VitalRepository;
import com.mustapha.medDesk.service.VitalService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class VitalServiceImpl implements VitalService { // Use 'implements'

    private final VitalRepository vitalRepository;
    private final MedicalRecordRepository medicalRecordRepository;
    private final VitalMapper vitalMapper;

    @Override
    public VitalDtoResponse create(VitalDtoRequest dto) {
        // 1. Fetch the Medical Record (The "Service Rule")
        MedicalRecord medicalRecord = medicalRecordRepository.findById(dto.getMedicalRecordId())
                .orElseThrow(() -> new ResourceNotFoundException("Medical Record not found with ID: " + dto.getMedicalRecordId()));

        // 2. Map DTO to Entity
        Vital vital = vitalMapper.toEntity(dto);

        // 3. Link the relationship manually
        vital.setMedicalRecord(medicalRecord);

        // 4. Calculate BMI automatically
        calculateBmi(vital);

        // 5. Save and return Response
        Vital savedVital = vitalRepository.save(vital);
        return vitalMapper.toDto(savedVital);
    }

    @Override
    public VitalDtoResponse update(Long id, VitalDtoRequest dto) {
        Vital existingVital = vitalRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vitals not found with ID: " + id));

        // Update simple fields from DTO
        vitalMapper.updateVitalFromDto(dto, existingVital);

        // Recalculate BMI in case weight/height changed
        calculateBmi(existingVital);

        return vitalMapper.toDto(vitalRepository.save(existingVital));
    }

    @Override
    @Transactional(readOnly = true)
    public List<VitalDtoResponse> getAll() {
        return vitalRepository.findAll().stream()
                .map(vitalMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public VitalDtoResponse getById(Long id) {
        return vitalRepository.findById(id)
                .map(vitalMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Vitals not found"));
    }

    @Override
    public void delete(Long id) {
        if (!vitalRepository.existsById(id)) {
            throw new ResourceNotFoundException("Cannot delete: Vitals not found");
        }
        vitalRepository.deleteById(id);
    }

    /**
     * Internal helper to calculate BMI
     * Formula: weight (kg) / [height (m)]^2
     */
    private void calculateBmi(Vital vital) {
        if (vital.getWeight() != null && vital.getHeight() != null && vital.getHeight() > 0) {
            double heightInMeters = vital.getHeight() / 100;
            double bmi = vital.getWeight() / (heightInMeters * heightInMeters);
            // Round to 1 decimal place
            vital.setBmi(Math.round(bmi * 10.0) / 10.0);
        }
    }
}