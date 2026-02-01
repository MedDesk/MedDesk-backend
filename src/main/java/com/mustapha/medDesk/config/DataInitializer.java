//package com.mustapha.medDesk.config;
//
//import jakarta.annotation.PostConstruct;
//import lombok.RequiredArgsConstructor;
//
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Component;
//
//@Component
//@RequiredArgsConstructor
//public class DataInitializer {
//
////    private final UserRepository userRepository;
//    private final PasswordEncoder passwordEncoder;
//
//    @PostConstruct
//    public void init() {
////        if (userRepository.count() == 0) {   // only if database is empty
////            User admin = new User();
////            admin.setUsername("admin");
////            admin.setPassword(passwordEncoder.encode("admin123"));
////            admin.setRole(UserRole.ADMIN);
////
////            userRepository.save(admin);
////            System.out.println(" Default ADMIN created: admin / admin123");
////        }
//    }
//
//}
