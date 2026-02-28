package com.mustapha.medDesk.service.impl;

import com.mustapha.medDesk.dto.request.MedicalRecord.MedicalRecordDtoRequest;
import com.mustapha.medDesk.dto.response.MedicalRecord.MedicalRecordDtoResponse;
import com.mustapha.medDesk.enums.StaffType;
import com.mustapha.medDesk.exception.ResourceNotFoundException;
import com.mustapha.medDesk.exception.ValidationException;
import com.mustapha.medDesk.mapper.MedicalRecordMapper;
import com.mustapha.medDesk.model.*;
import com.mustapha.medDesk.repository.*;
import com.mustapha.medDesk.service.MedicalRecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional // Defaults all methods to transactional
public class MedicalRecordServiceImpl implements MedicalRecordService {

    private final MedicalRecordRepository medicalRecordRepository;
    private final MedicalRecordMapper medicalRecordMapper;

    // You need these to verify foreign keys exist before saving
    private final AppointmentReposiotry appointmentRepository;
    private final DoctorRepository doctorRepository;
    private final StaffRepository staffRepository;
    private final VitalRepository vitalRepository;

    @Override
    public MedicalRecordDtoResponse create(MedicalRecordDtoRequest dto) {
        // 1. Validation Logic
        if (medicalRecordRepository.existsByAppointmentId(dto.getAppointmentId())) {
            throw new ValidationException("Medical record already exists for this appointment.");
        }

        // 2. Fetch real objects from DB (This ensures they exist and are valid)
        Staff nurse = staffRepository.findById(dto.getNurseId())
                .orElseThrow(() -> new ResourceNotFoundException("Nurse not found"));

        if (nurse.getStaffType() != StaffType.NURSE) {
            throw new ValidationException("Selected staff is not a nurse");
        }

        Doctor doctor = doctorRepository.findById(dto.getDoctorId())
                .orElseThrow(() -> new ResourceNotFoundException("Doctor not found"));

        Appointment appointment = appointmentRepository.findById(dto.getAppointmentId())
                .orElseThrow(() -> new ResourceNotFoundException("Appointment not found"));

        // 3. Map the simple fields
        MedicalRecord medicalRecord = medicalRecordMapper.toEntity(dto);

        // 4. MANUALLY set the objects we fetched
        medicalRecord.setNurse(nurse);
        medicalRecord.setDoctor(doctor);
        medicalRecord.setAppointment(appointment);

        // Set vital if provided (optional)
        if (dto.getVitalId() != null) {
            Vital vital = vitalRepository.findById(dto.getVitalId())
                    .orElseThrow(() -> new ResourceNotFoundException("Vitals not found"));
            medicalRecord.setVital(vital);
        }

        return medicalRecordMapper.toDto(medicalRecordRepository.save(medicalRecord));
    }
    @Override
    public MedicalRecordDtoResponse update(Long id, MedicalRecordDtoRequest dto) {
        MedicalRecord medicalRecord = medicalRecordRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Medical record not found with id: " + id));

        // Ensure we aren't changing to an appointment that belongs to another record
        if (!medicalRecord.getAppointment().getId().equals(dto.getAppointmentId())) {
            if (medicalRecordRepository.existsByAppointmentId(dto.getAppointmentId())) {
                throw new ValidationException("The new appointment ID is already linked to another record.");
            }
        }
        validateRelatedEntities(dto);

        medicalRecordMapper.updateMedicalRecordForm(dto, medicalRecord);
        return medicalRecordMapper.toDto(medicalRecordRepository.save(medicalRecord));
    }

    @Override
    @Transactional(readOnly = true) // Performance optimization
    public Page<MedicalRecordDtoResponse> getAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return medicalRecordRepository.findAll(pageable)
                .map(medicalRecordMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public MedicalRecordDtoResponse getById(Long id) {
        return medicalRecordRepository.findById(id)
                .map(medicalRecordMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Medical record not found"));
    }

    @Override
    public void delete(Long id) {
        if (!medicalRecordRepository.existsById(id)) {
            throw new ResourceNotFoundException("Cannot delete: Medical record not found");
        }
        medicalRecordRepository.deleteById(id);
    }

    /**
     * Helper method to ensure data integrity
     */
    private void validateRelatedEntities(MedicalRecordDtoRequest dto) {
        if (!appointmentRepository.existsById(dto.getAppointmentId())) {
            throw new ResourceNotFoundException("Appointment not found");
        }
        if (!doctorRepository.existsById(dto.getDoctorId())) {
            throw new ResourceNotFoundException("Doctor not found");
        }
        if (!staffRepository.existsById(dto.getNurseId())) {
            throw new ResourceNotFoundException("Nurse (Staff) not found");
        }
    }
}