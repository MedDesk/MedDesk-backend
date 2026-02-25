package com.mustapha.medDesk.repository;

import com.mustapha.medDesk.model.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.Optional;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    Optional<Doctor> findById(Long id);
    Optional<Doctor> findByEmail(String email);
    Optional<Doctor> findByUsername(String username);


}
