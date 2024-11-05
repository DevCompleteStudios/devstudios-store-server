package com.devstudios.store.devstudios_store_server.application.interfaces.services;


public interface IBcryptService {

    public String hashPassword( String password );
    public Boolean comparePassword( String password, String passwordHash );

}
