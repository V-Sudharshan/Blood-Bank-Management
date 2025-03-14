package com.bloodbank.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // Disable CSRF protection
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Use stateless sessions
            .authorizeHttpRequests(auth -> auth
                // Public endpoints
                .requestMatchers(
                    "/api/auth/**", // Allow all authentication endpoints
                    "/api/donors/**", // Allow all donor-related endpoints
                    "/api/inventory/**", // Allow all inventory-related endpoints
                    "/api/requests/**", // Allow all request-related endpoints
                    "/api/donations/**" // Allow all donation-related endpoints
                ).permitAll()
                // Static resources
                .requestMatchers(
                    "/", // Allow the root path
                    "/index.html", // Allow the index page
                    "/css/**", // Allow CSS files
                    "/js/**", // Allow JavaScript files
                    "/images/**", // Allow image files
                    "/favicon.ico" // Allow the favicon
                ).permitAll()
                // Secure all other endpoints
                .anyRequest().authenticated()
            );

        return http.build();
    }
}