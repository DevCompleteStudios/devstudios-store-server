package com.devstudios.store.devstudios_store_server.config;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

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
                .requestMatchers(
                    "/api/auth/**",
                    "/api/scripts/find-all",
                    "/images/**",
                    "/api/scripts/find-by-id/{id}",
                    "/webhook",
                    "api/rating/**",
                    "/api/subscriptions/get-all"
                ).permitAll()
                .anyRequest().authenticated()
            )
            .csrf( config -> config.disable() )
            .cors( config -> config.configurationSource(corsConfigurationSource()) )
            .addFilterBefore(validationFilter, UsernamePasswordAuthenticationFilter.class)
            .build();
    }


    @Bean
    UrlBasedCorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:4200"));
        configuration.setAllowedMethods(Arrays.asList("GET","POST", "PUT", "DELETE"));
        configuration.setAllowedHeaders(Arrays.asList("*")); // Permite todos los encabezados
        configuration.setAllowCredentials(true); // Permite el envío de credenciales
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

}
