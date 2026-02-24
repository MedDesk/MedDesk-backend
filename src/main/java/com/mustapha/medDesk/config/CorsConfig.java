package com.mustapha.medDesk.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
public class CorsConfig {

    /**
     * Defines the CORS configuration source used by the security filter chain.
     * This setup ensures that cross-origin requests from supported frontends
     * are permitted while maintaining security constraints.
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        /* Define authorized origins to permit local development servers */
        configuration.setAllowedOrigins(List.of(
                "http://localhost:3000",
                "http://localhost:4200"
        ));

        /* Enable standard and custom HTTP methods required for RESTful APIs */
        configuration.setAllowedMethods(List.of(
                "GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"
        ));

        /* Allow all headers to prevent issues with common client-side libraries */
        configuration.setAllowedHeaders(List.of("*"));

        /* Enable support for user credentials such as cookies or authorization headers */
        configuration.setAllowCredentials(true);

        /* Apply this configuration globally to all application endpoints */
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }
}