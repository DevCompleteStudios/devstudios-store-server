package com.devstudios.store.devstudios_store_server.application.interfaces.services;

import java.util.UUID;



public interface IRandomService {

    public String randomsDigits( int count );
    public UUID uuid();

}
