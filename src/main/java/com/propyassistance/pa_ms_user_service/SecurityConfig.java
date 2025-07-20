package com.propyassistance.pa_ms_user_service;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    /*@Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable() // deprecated
                .authorizeHttpRequests() // deprecated
                .anyRequest().permitAll(); // 🔓 Allow all requests without auth

        return http.build();
    }*/
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .securityMatcher("/**") // Apply to all routes
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll()  // 🔓 Permit all requests
                )
                .csrf(csrf -> csrf.disable()); // 🔒 Disable CSRF (for APIs only)

        return http.build();
    }
}
