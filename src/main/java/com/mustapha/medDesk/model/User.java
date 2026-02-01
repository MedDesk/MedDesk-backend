package com.mustapha.medDesk.model;

import com.mustapha.medDesk.enums.Gender;
import com.mustapha.medDesk.enums.UserRole;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
@Entity
@Table(name = "users")
@Data
@Getter
@Setter
public class User extends BaseEntity {

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(unique = true, nullable = false)
    private String username;

    @Email
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = true)
    private LocalDate birthDate;

    @Column(nullable = false)
    private Gender gender;

    @Column(nullable = true)
    private String address;

    @Column(nullable = true)
    private String CIN;

    @Column(nullable = true)
    private String phone;

    @Column(nullable = true)
    private String profileImage;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRole role = UserRole.OTHER; // default role

    @Column(nullable = false)
    private int cabinetId = 1; // default cabinet for easest scaling  in the future
}
