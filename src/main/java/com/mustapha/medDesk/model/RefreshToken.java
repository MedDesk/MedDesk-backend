package com.mustapha.medDesk.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "refresh_token")
@Getter
@Setter
public class RefreshToken {

    @Id
    @GeneratedValue
    private Long id;
    private String token;
    private String email;
    private boolean revoked;
    private Date expiryDate;

}
