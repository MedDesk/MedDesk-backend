package com.mustapha.medDesk.config;

import com.mustapha.medDesk.enums.Gender;
import com.mustapha.medDesk.enums.UserRole;
import com.mustapha.medDesk.model.User;
import com.mustapha.medDesk.repository.UserRepository;
import com.mustapha.medDesk.util.PasswordUtil;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class DataInitializer {
    private final UserRepository userRepository;



    @PostConstruct
    public void init(){
        boolean isSuperAdminExists = userRepository.existsByRole(UserRole.SUPER_ADMIN);

        if(!isSuperAdminExists){
            User superAdmin = new User();
            superAdmin.setFirstName("admin");
            superAdmin.setLastName("admin");
            superAdmin.setUsername("superadmin");
            superAdmin.setPassword(PasswordUtil.hash("superadminpassword"));
            superAdmin.setEmail("superadmin@gmail.com");
            superAdmin.setGender(Gender.MALE);
            superAdmin.setRole(UserRole.SUPER_ADMIN);

            userRepository.save(superAdmin);
            System.out.println("==========================================");
            System.out.println("super admin account created successfully");
            System.out.println("==========================================");
        }
    }

}