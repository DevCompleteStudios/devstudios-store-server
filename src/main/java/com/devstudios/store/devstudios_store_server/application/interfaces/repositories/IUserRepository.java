package com.devstudios.store.devstudios_store_server.application.interfaces.repositories;

import java.util.Optional;

import com.devstudios.store.devstudios_store_server.application.interfaces.projections.IUserProjection;
import com.devstudios.store.devstudios_store_server.domain.entities.UserEntity;





public interface IUserRepository {

    public UserEntity save(UserEntity user);
    public Optional<UserEntity> findById(Long id);
    public Optional<UserEntity> findByEmail(String email);
    public Optional<IUserProjection> findUserById( Long id );
    public Optional<IUserProjection> findUserByEmail( String email );

}
