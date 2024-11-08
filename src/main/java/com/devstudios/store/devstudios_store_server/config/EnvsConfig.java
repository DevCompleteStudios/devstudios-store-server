package com.devstudios.store.devstudios_store_server.config;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Base64;
import java.util.Date;

import javax.crypto.SecretKey;

import io.github.cdimascio.dotenv.Dotenv;
import io.jsonwebtoken.security.Keys;




public abstract class EnvsConfig {

    private static final String KEY_JWT = Dotenv.load().get("JWT_KEY");
    private static final int MINUTES_EXPIRED_JWT = 60;


    public static SecretKey getKeyJwt(){
        return Keys.hmacShaKeyFor(Base64.getDecoder().decode(KEY_JWT));
    }

    public static Date getJwtExpirationDate() {
        LocalDateTime expirationDateTime = LocalDateTime.now().plusMinutes(MINUTES_EXPIRED_JWT);
        return Date.from(expirationDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

}
