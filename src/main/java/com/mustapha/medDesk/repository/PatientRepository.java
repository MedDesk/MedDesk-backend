package com.mustapha.medDesk.repository;

import com.mustapha.medDesk.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {

    Optional<Patient>findById(Long id);
    Optional<Patient> findByEmail(String email);
    boolean isExistsById(Long id);
}
