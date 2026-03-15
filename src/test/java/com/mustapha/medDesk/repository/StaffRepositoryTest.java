package com.mustapha.medDesk.repository;

import com.mustapha.medDesk.enums.*;
import com.mustapha.medDesk.model.Staff;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest // this setup the in-memory dabatabse for testing
class StaffRepositoryTest {

    @Autowired
    private StaffRepository staffRepository;

    private Staff savedStaff;

    @BeforeEach
    void setUp() {
        /**
         * we create a new staff and we fill every mandatory field
         * otherwise dabatabse will throw NULL error
         */
        Staff staff = new Staff();
        // user fields
        staff.setFirstName("Joy");
        staff.setLastName("Nurse");
        staff.setEmail("joy@test.com");
        staff.setUsername("nurse_joy");
        staff.setPassword("password123");

        // staff fields
        staff.setStaffType(StaffType.NURSE);
        staff.setEmploymentStatus(EmploymentStatus.ACTIVE);
        staff.setContractType(ContractType.FULL_TIME);
        staff.setShiftPreference(ShiftPreference.DAY);
        staff.setStaffSpecialist(StaffSpecialist.ANESTHESIOLOGIST);

        savedStaff = staffRepository.save(staff);
    }

    @Test
    void findByEmail_ShouldWork() {
        /**
         * we search for staff using the email and check if found
         */
        Optional<Staff> found = staffRepository.findByEmail("joy@test.com");

        assertTrue(found.isPresent());
        assertEquals("nurse_joy", found.get().getUsername());
    }

    @Test
    void findById_ShouldWork() {
        /**
         * we test if we can find staff by the ID from dabatabse
         */
        Optional<Staff> found = staffRepository.findById(savedStaff.getId());

        assertTrue(found.isPresent());
        assertEquals("Joy", found.get().getFirstName());
    }

    @Test
    void existsByIdAndStaffType_ShouldReturnTrue() {
        /**
         * we check if staff exists with the correct ID and Type (NURSE)
         */
        boolean exists = staffRepository.existsByIdAndStaffType(savedStaff.getId(), StaffType.NURSE);

        assertTrue(exists);
    }

    @Test
    void existsByIdAndStaffType_ShouldReturnFalse_WhenTypeIsWrong() {
        /**
         * we check with the wrong type (RECEPTIONIST) and it should be false
         */
        boolean exists = staffRepository.existsByIdAndStaffType(savedStaff.getId(), StaffType.LAB_TECH);

        assertFalse(exists);
    }

    @Test
    void findByStaffType_ShouldReturnList() {
        /**
         * here we try to find all staff that are NURSES
         */
        List<Staff> staffList = staffRepository.findByStaffType(StaffType.NURSE);

        assertFalse(staffList.isEmpty());
        assertEquals(1, staffList.size());
        assertEquals("Joy", staffList.get(0).getFirstName());
    }
}