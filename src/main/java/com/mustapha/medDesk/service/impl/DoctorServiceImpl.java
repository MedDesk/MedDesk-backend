package com.mustapha.medDesk.service.impl;


import com.mustapha.medDesk.dto.request.doctor.DoctorDtoRequest;
import com.mustapha.medDesk.dto.response.doctor.DoctorDtoResponse;
import com.mustapha.medDesk.enums.UserRole;
import com.mustapha.medDesk.exception.ResourceNotFoundException;
import com.mustapha.medDesk.exception.ValidationException;
import com.mustapha.medDesk.mapper.DoctorMapper;
import com.mustapha.medDesk.model.Doctor;
import com.mustapha.medDesk.model.MedicalRecord;
import com.mustapha.medDesk.repository.DoctorRepository;
import com.mustapha.medDesk.service.DoctorService;
import com.mustapha.medDesk.util.PasswordUtil;
import jakarta.transaction.Transactional;
import jakarta.websocket.server.ServerEndpoint;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Getter @Setter
public class DoctorServiceImpl implements DoctorService {
    private final DoctorRepository doctorRepository;
    private final DoctorMapper doctorMapper;
    private final DoctorService doctorService;

    @Override
    public DoctorDtoResponse create(DoctorDtoRequest dto) {
        boolean isExist = doctorRepository.findByEmail(dto.getEmail()).isPresent();
        if(isExist){
            throw  new ValidationException("This Email already used");
        }
        Doctor doctor = doctorMapper.toEntity(dto);
        doctor.setPassword(PasswordUtil.hash(dto.getPassword()));
        doctor.setRole(UserRole.DOCTOR);

        Doctor savedDoctor = doctorRepository.save(doctor);
        return doctorMapper.toDto(savedDoctor);

    }

    @Override
    public DoctorDtoResponse update(Long id, DoctorDtoRequest dto) {
        // find existing doctor
        Doctor doctor = doctorRepository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("This user not found")
        );
        // update the fileds except the password handle by mapstruct ignore
        doctorMapper.updateDoctorForm(dto, doctor);
        // handle password hashing password
        if(dto.getPassword() != null && !dto.getPassword().isBlank()){
            doctor.setPassword(PasswordUtil.hash(dto.getPassword()));
        }

        // save doctor entity
        Doctor savedDoctor = doctorRepository.save(doctor);

        DoctorDtoResponse response = doctorMapper.toDto(savedDoctor);

        // get all doctors medicalREcirds and retunr there ids
        if(savedDoctor.getMedicalRecords() != null){
            List<Long>medicalRecordIds = savedDoctor.getMedicalRecords().stream()
                    .map(record -> record.getId()).toList();
            response.setMedicalRecordIds(medicalRecordIds);
        }

        return response;

    }



    @Override
    public DoctorDtoResponse findById(Long id) {
        Doctor doctor = doctorRepository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("This user not found")
        );
        return doctorMapper.toDto(doctor);
    }

    @Override
    public Page<DoctorDtoResponse> getAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return doctorRepository.findAll(pageable).map(
                doctor-> doctorMapper.toDto(doctor)
        );
    }

    @Override
    public void delete(Long id) {
        Doctor doctor = doctorRepository.findById(id).orElseThrow(
                ()-> new ValidationException("User not found")
        );
        doctorRepository.delete(doctor);
    }
}
