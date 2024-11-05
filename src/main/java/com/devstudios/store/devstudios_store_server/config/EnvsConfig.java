package com.devstudios.store.devstudios_store_server.config;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import javax.crypto.SecretKey;

import io.jsonwebtoken.Jwts;


public abstract class EnvsConfig {

    private static final SecretKey JWT_KEY = Jwts.SIG.HS256.key().build();
    private static final int MINUTES_EXPIRED_JWT = 60;


    public static SecretKey getKeyJwt(){
        return JWT_KEY;
    }

    public static Date getJwtExpirationDate() {
        LocalDateTime expirationDateTime = LocalDateTime.now().plusMinutes(MINUTES_EXPIRED_JWT);
        return Date.from(expirationDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

}
