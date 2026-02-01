package com.mustapha.medDesk.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@MappedSuperclass // means this table is nto showed in the db but is contains the communs fields in all tables
public abstract class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "created_date", updatable = false) // we cannot updated it
    private LocalDateTime createdDate;

    @Column(name = "updated_date")
    private LocalDate updatedDate;

    @Column(name = "created_by", updatable = false)
    private String createdBy;

    @PrePersist
    protected void onCreate(){
        createdDate = LocalDateTime.now();
    }
}
