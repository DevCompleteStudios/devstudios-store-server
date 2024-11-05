package com.devstudios.store.devstudios_store_server.infrastructure.services;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.devstudios.store.devstudios_store_server.application.interfaces.services.IBcryptService;



@Service
public class BcryptServiceImpl implements IBcryptService {

    private static final PasswordEncoder encode = new BCryptPasswordEncoder();


    @Override
    public String hashPassword(String password) {
        return encode.encode(password);
    }

    @Override
    public Boolean comparePassword(String password, String passwordHash) {
        return encode.matches(password, passwordHash);
    }

}
