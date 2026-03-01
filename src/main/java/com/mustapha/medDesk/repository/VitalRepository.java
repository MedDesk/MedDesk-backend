package com.mustapha.medDesk.repository;

import com.mustapha.medDesk.model.Vital;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VitalRepository extends JpaRepository<Vital, Long> {
    Optional<Vital> findById(Long id);

}
