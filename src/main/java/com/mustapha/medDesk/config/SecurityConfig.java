package com.mustapha.medDesk.config;

import com.mustapha.medDesk.security.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfigurationSource;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity // Allows you to use @PreAuthorize("hasRole('ADMIN')") on controllers
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http,
            CorsConfigurationSource corsConfigurationSource) throws Exception {

        http
                // Disable CSRF (Stateless APIs don't need it)
                .csrf(AbstractHttpConfigurer::disable)

                // Enable CORS using your defined source
                .cors(cors -> cors.configurationSource(corsConfigurationSource))

                //  Set Authorization Rules
                .authorizeHttpRequests(auth -> auth
                        // Allow anyone to attempt login or registration
                        .requestMatchers("/api/v1/auth/**").permitAll()

                        // (Optional) Allow Swagger/OpenAPI documentation if you use it
                        .requestMatchers("/v3/api-docs/**", "/swagger-ui/**").permitAll()

                        // Everything else MUST be authenticated
                        .anyRequest().authenticated()
                )

                //Set Session Management to STATELESS (No Cookies)
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                // Connect my Authentication Provider (from ApplicationConfig)
                .authenticationProvider(authenticationProvider)

                // Add the JWT Filter BEFORE the standard UsernamePassword filter
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
