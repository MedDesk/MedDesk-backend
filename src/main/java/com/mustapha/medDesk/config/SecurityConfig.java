package com.mustapha.medDesk.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfigurationSource;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http,
            CorsConfigurationSource corsConfigurationSource) throws Exception {

        return http
                // Disable CSRF for API compatibility
                .csrf(AbstractHttpConfigurer::disable)

                // Integrate the defined CORS configuration
                .cors(cors -> cors.configurationSource(corsConfigurationSource))

                // Configure request authorization
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/**").permitAll()
                )

                .build();
    }
}