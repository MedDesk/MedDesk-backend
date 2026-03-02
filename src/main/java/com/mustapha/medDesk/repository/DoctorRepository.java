package com.mustapha.medDesk.repository;

import com.mustapha.medDesk.model.Doctor;
import com.mustapha.medDesk.model.MedicalRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    Optional<Doctor> findById(Long id);
    Optional<Doctor> findByEmail(String email);
    Optional<Doctor> findByUsername(String username);
    boolean existsById(Long id);


    // get medicalRecords by id
    @Query("SELECT d FROM Doctor d LEFT JOIN FETCH d.medicalRecords WHERE d.id = :id")
    Optional<Doctor> findByIdWithRecords(@Param("id") Long id);
}
