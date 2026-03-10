package com.mustapha.medDesk.repository;

import com.mustapha.medDesk.model.MedicalRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface MedicalRecordRepository extends JpaRepository<MedicalRecord, Long> {
    Optional<MedicalRecord> findById(Long id);
    Optional<MedicalRecord>findMedicalRecordByAppointment_Id(Long appointmentId);

     boolean existsByAppointmentId(Long id);

    @Query("SELECT mr FROM MedicalRecord mr " +
            "JOIN FETCH mr.appointment app " +
            "JOIN app.patient p " +
            "WHERE p.id = :patientId")
    List<MedicalRecord> findAllByPatientId(@Param("patientId") Long patientId);
}


