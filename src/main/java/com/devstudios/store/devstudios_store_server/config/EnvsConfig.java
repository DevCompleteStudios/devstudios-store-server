package com.devstudios.store.devstudios_store_server.config;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Base64;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.security.Keys;



@Component
public class EnvsConfig {

    @Value("${jwt.key}")
    private String keyJwt;

    private static final int MINUTES_EXPIRED_JWT = 60;

    public SecretKey getKeyJwt() {
        return Keys.hmacShaKeyFor(Base64.getDecoder().decode(keyJwt));
    }

    public Date getJwtExpirationDate() {
        LocalDateTime expirationDateTime = LocalDateTime.now().plusMinutes(MINUTES_EXPIRED_JWT);
        return Date.from(expirationDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }
}
