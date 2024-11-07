package com.devstudios.store.devstudios_store_server.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.devstudios.store.devstudios_store_server.infrastructure.services.JwtValidationFilter;




@Configuration
@EnableWebSecurity
@EnableMethodSecurity(
    prePostEnabled = true,
    securedEnabled = true,
    jsr250Enabled = true
)
public class SecurityConfig {

    private final JwtValidationFilter validationFilter;

    public SecurityConfig( JwtValidationFilter validationFilter ){
        this.validationFilter = validationFilter;
    }


    @Bean
    SecurityFilterChain securityFilterChain( HttpSecurity http ) throws Exception {
        return http
            .authorizeHttpRequests( authz -> authz
                .requestMatchers("/api/auth/**", "/api/scripts/find-all", "/images/**").permitAll()
                .anyRequest().authenticated()
            )
            .csrf( config -> config.disable() )
            .addFilterBefore(validationFilter, UsernamePasswordAuthenticationFilter.class)
            .build();
    }


}
