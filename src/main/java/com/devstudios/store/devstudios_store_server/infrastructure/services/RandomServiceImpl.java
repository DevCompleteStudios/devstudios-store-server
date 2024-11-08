package com.devstudios.store.devstudios_store_server.infrastructure.services;

import java.util.Random;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.devstudios.store.devstudios_store_server.application.interfaces.services.IRandomService;



@Service
public class RandomServiceImpl implements IRandomService {

    private final Random RANDOM = new Random();


    @Override
    public String randomsDigits(int count) {
        String code = "";

        for( int i = 0; i < count; i++ ){
            code += RANDOM.nextInt(0, 9);
        }

        return code;
    }

    @Override
    public UUID uuid() {
        return UUID.randomUUID();
    }



}
