package com.mustapha.medDesk.model;

import com.mustapha.medDesk.enums.*;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

public class Staff extends User{


    @Enumerated(EnumType.STRING)
    @Column(name = "staff_specialist", nullable = false)
    private StaffSpecialist staffSpecialist;

    @Enumerated(EnumType.STRING)
    @Column(name = "shift_preference", nullable = false)
    private ShiftPreference shiftPreference;

    @Enumerated(EnumType.STRING)
    @Column(name = "employee_status", nullable = false)
    private EmploymentStatus employmentStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "contract_type", nullable = true)
    private ContractType contractType = ContractType.OTHER;

    @Enumerated(EnumType.STRING)
    @Column(name = "staff_type", nullable = false)
    private StaffType staffType; // like role ex: nurse / tech

}
