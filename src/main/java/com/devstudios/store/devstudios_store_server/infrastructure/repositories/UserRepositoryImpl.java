package com.devstudios.store.devstudios_store_server.infrastructure.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.devstudios.store.devstudios_store_server.application.interfaces.repositories.IUserRepository;
import com.devstudios.store.devstudios_store_server.application.interfaces.repositories.IUserRepositoryJpa;
import com.devstudios.store.devstudios_store_server.domain.entities.UserEntity;





@Repository
public class UserRepositoryImpl implements IUserRepository {

    @Autowired
    IUserRepositoryJpa repositoryJpa;


    @Override
    public UserEntity save(UserEntity user) {
        return repositoryJpa.save(user);
    }



}
