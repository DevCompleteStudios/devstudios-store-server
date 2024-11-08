package com.devstudios.store.devstudios_store_server.infrastructure.repositories;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.devstudios.store.devstudios_store_server.application.interfaces.projections.IUserProjection;
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

    @Override
    public Optional<UserEntity> findById(Long id) {
        return repositoryJpa.findById(id);
    }

    @Override
    public Optional<UserEntity> findByEmail(String email) {
        return repositoryJpa.findOneByEmail(email);
    }

    @Override
    public Optional<IUserProjection> findUserById(Long id) {
        return repositoryJpa.findOneUserById(id);
    }

    @Override
    public Optional<IUserProjection> findUserByEmail(String email) {
        return repositoryJpa.findOneUserByEmail(email);
    }

    @Override
    public Optional<UserEntity> findByCodeAuth(String code) {
        return repositoryJpa.findByCodeAuth_Code(code);
    }


}
