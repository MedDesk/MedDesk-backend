package com.mustapha.medDesk.model;

import com.mustapha.medDesk.enums.DayOfWeek;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;

@Entity
@Table(name = "working_hours")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class WorkingHours {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    @Enumerated(EnumType.STRING)
    private DayOfWeek dayOfWeek;  // MONDAY, TUESDAY ...

    @Column(nullable = false)
    private LocalTime startTime;

    @Column(nullable = false)
    private LocalTime endTime;


    @Column(nullable = false)
    private boolean active; // working available or holiday
}
