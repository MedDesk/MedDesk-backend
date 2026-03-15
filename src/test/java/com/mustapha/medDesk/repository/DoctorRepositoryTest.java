package com.mustapha.medDesk.repository;

import com.mustapha.medDesk.enums.DoctorSpeacialist;
import com.mustapha.medDesk.model.Doctor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest // this annotation setup a small dabatabse in memory for testing
class DoctorRepositoryTest {

    @Autowired
    private DoctorRepository doctorRepository;

    private Doctor savedDoctor;

    @BeforeEach
    void setUp() {
        /**
         * here we prepare a doctor and we save him in dabatabse before every test
         */
        Doctor doctor = new Doctor();
        doctor.setFirstName("Mustapha");
        doctor.setLastName("Moutaki");
        doctor.setEmail("mustapha@example.com");
        doctor.setUsername("mustapha_001");
        doctor.setPassword("passworrd");


        // doctor fields
        doctor.setSpecialist(DoctorSpeacialist.CARDIOLOGY);
        doctor.setLicenseNumber("DOC12345");
        doctor.setEmergencyContact("0600000000");
        // we save him and keep the saved object with the ID
        savedDoctor = doctorRepository.save(doctor);
    }

    @Test
    void findById_ShouldReturnDoctor() {
        /**
         * we check if we can find doctor by his ID from dabatabse
         */
        Optional<Doctor> found = doctorRepository.findById(savedDoctor.getId());

        // we check if he is present and the name is correct
        assertTrue(found.isPresent());
        assertEquals("Mustapha", found.get().getFirstName());
    }

    @Test
    void findByEmail_ShouldWork() {
        /**
         * we test if our custom query findByEmail works or not
         */
        Optional<Doctor> found = doctorRepository.findByEmail("mustapha@example.com");

        assertTrue(found.isPresent());
        assertEquals("mustapha_001", found.get().getUsername());
    }

    @Test
    void findByUsername_ShouldWork() {
        /**
         * here we try to find doctor by his username
         */
        Optional<Doctor> found = doctorRepository.findByUsername("mustapha_001");

        assertTrue(found.isPresent());
        assertEquals("Mustapha", found.get().getFirstName());
    }

    @Test
    void existsById_ShouldReturnTrue() {
        /**
         * check if existById return true for the doctor we saved
         */
        boolean exists = doctorRepository.existsById(savedDoctor.getId());
        assertTrue(exists);
    }

    @Test
    void delete_ShouldRemoveDoctor() {
        /**
         * here we delete the doctor and check if he still exist or not
         */
        doctorRepository.deleteById(savedDoctor.getId());

        Optional<Doctor> found = doctorRepository.findById(savedDoctor.getId());
        assertFalse(found.isPresent());
    }
}
