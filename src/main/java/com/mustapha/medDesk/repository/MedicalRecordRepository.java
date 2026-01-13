package com.mustapha.medDesk.repository;

import com.mustapha.medDesk.model.MedicalRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface MedicalRecordRepository extends JpaRepository<MedicalRecord, Long> {
    Optional<MedicalRecord> findById(Long id);
    Optional<MedicalRecord>findMedicalRecordByAppointment_Id(Long appointmentId);
     boolean existsByAppointmentId(Long id);
}


