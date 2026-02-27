package com.mustapha.medDesk.repository;

import com.mustapha.medDesk.enums.DayOfWeek;
import com.mustapha.medDesk.model.WorkingHours;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
    public interface WorkingHoursRepository extends JpaRepository<WorkingHours, Long> {

        Optional<WorkingHours> findByDayOfWeekAndActiveTrue(DayOfWeek dayOfWeek);
        Optional<WorkingHours> findByDayOfWeek(DayOfWeek dayOfWeek);
    }

