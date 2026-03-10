package com.mustapha.medDesk.repository;

import com.mustapha.medDesk.enums.StaffType;
import com.mustapha.medDesk.enums.UserRole;
import com.mustapha.medDesk.model.Staff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Repository
public interface StaffRepository extends JpaRepository<Staff, Long> {
    Optional<Staff> findByEmail(String email);
    Optional<Staff> findById(long id);
    // Check if a staff exists and has a specific role
    boolean existsByIdAndStaffType(Long id, StaffType staffType);

    List<Staff> findByStaffType(StaffType staffType);
}
