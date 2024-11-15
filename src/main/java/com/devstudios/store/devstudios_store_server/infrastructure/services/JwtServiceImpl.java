package com.devstudios.store.devstudios_store_server.infrastructure.services;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import com.devstudios.store.devstudios_store_server.application.interfaces.services.IJwtService;
import com.devstudios.store.devstudios_store_server.config.EnvsConfig;
import com.devstudios.store.devstudios_store_server.domain.entities.RoleEntity;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;



@Service
public class JwtServiceImpl implements IJwtService {

    @Autowired
    EnvsConfig envsConfig;

    @Override
    public String createJwt(List<RoleEntity> roles, String email) {
        Claims claims = Jwts.claims()
            .add("authorities", rolesToClaims(roles))
            .build();

        String jwt = Jwts.builder()
            .subject(email)
            .signWith(envsConfig.getKeyJwt())
            .expiration(envsConfig.getJwtExpirationDate())
            .claims(claims)
            .compact();

        return jwt;
    }


    private Collection<GrantedAuthority> rolesToClaims( List<RoleEntity> roles ){
        return roles.stream()
            .map( r -> new SimpleGrantedAuthority(r.getRole()))
            .collect(Collectors.toList());
    }

}
