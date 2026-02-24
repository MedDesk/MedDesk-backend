package com.mustapha.medDesk.model;

import com.mustapha.medDesk.enums.*;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "staff")
@Getter
@Setter
@Data
@EqualsAndHashCode(callSuper = true) //this used for getting the important data to compare it
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
