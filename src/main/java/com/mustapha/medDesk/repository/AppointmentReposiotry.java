package com.mustapha.medDesk.repository;

import com.mustapha.medDesk.model.Appointment;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;

@Repository
public interface AppointmentReposiotry extends JpaRepository<Appointment, Long> {
    Optional<Appointment> findById(Long id);

    boolean existsByScheduleTimeStartLessThanAndScheduleTimeEndGreaterThan(
            LocalDateTime end,
            LocalDateTime start
    );

    boolean existsByAppointmentDateAndScheduleTimeStartAndScheduleTimeEnd(@NotNull(message = "Appointment date is required") LocalDate appointmentDate, LocalTime slotStart, LocalTime slotEnd);

    Optional<Appointment> findByAppointmentDateAndScheduleTimeStartAndScheduleTimeEnd(
            LocalDate date, LocalTime start, LocalTime end
    );
}
