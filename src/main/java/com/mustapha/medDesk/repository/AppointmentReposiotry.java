package com.mustapha.medDesk.repository;

import com.mustapha.medDesk.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface AppointmentReposiotry extends JpaRepository<Appointment, Long> {
    Optional<Appointment> findById(Long id);

    boolean existsByScheduleTimeStartLessThanAndScheduleTimeEndGreaterThan(
            LocalDateTime end,
            LocalDateTime start
    );
}
