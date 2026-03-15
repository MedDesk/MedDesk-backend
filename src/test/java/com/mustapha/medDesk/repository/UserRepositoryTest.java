package com.mustapha.medDesk.repository;

import com.mustapha.medDesk.model.User;
import com.mustapha.medDesk.enums.UserRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest // this annotation setup a small dabatabse for testing
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    private User savedUser;

    @BeforeEach
    void setUp() {
        /**
         * here we create a new user and we save him in dabatabse for every test
         */
        User user = new User();
        user.setFirstName("Mustapha");
        user.setLastName("Test");
        user.setEmail("mustapha@test.com");
        user.setUsername("mustapha_dev");
        user.setPassword("password123");
        user.setRole(UserRole.ADMIN);

        savedUser = userRepository.save(user);
    }

    @Test
    void findByEmail_ShouldWork() {
        /**
         * we search for the user by his email and check if we find him
         */
        Optional<User> found = userRepository.findByEmail("mustapha@test.com");

        // we check if he is present and if username is correct
        assertTrue(found.isPresent());
        assertEquals("mustapha_dev", found.get().getUsername());
    }

    @Test
    void findByUsername_ShouldWork() {
        /**
         * here we try to find the user using his username
         */
        Optional<User> found = userRepository.findByUsername("mustapha_dev");

        assertTrue(found.isPresent());
        assertEquals("mustapha@test.com", found.get().getEmail());
    }

    @Test
    void findById_ShouldWork() {
        /**
         * we test if we can find user using his ID from dabatabse
         */
        Optional<User> found = userRepository.findById(savedUser.getId());

        assertTrue(found.isPresent());
        assertEquals("Mustapha", found.get().getFirstName());
    }

    @Test
    void existsByRole_ShouldReturnTrue() {
        /**
         * we check if the role exists in our dabatabse or not
         */
        boolean exists = userRepository.existsByRole(UserRole.ADMIN);

        assertTrue(exists);
    }

    @Test
    void existsByEmail_ShouldReturnTrue() {
        /**
         * we check if email already exist in dabatabse or not
         */
        boolean exists = userRepository.existsByEmail("mustapha@test.com");

        assertTrue(exists);
    }

    @Test
    void existsByEmail_ShouldReturnFalse_WhenEmailIsNew() {
        /**
         * here we check with email that doesn't exist to see if it return false
         */
        boolean exists = userRepository.existsByEmail("wrong@email.com");

        assertFalse(exists);
    }
}