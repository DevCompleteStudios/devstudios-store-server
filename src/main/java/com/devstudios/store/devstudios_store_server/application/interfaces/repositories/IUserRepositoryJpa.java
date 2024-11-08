package com.devstudios.store.devstudios_store_server.application.interfaces.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.devstudios.store.devstudios_store_server.application.interfaces.projections.IUserProjection;
import com.devstudios.store.devstudios_store_server.domain.entities.UserEntity;



public interface IUserRepositoryJpa extends JpaRepository<UserEntity, Long> {
    public Optional<UserEntity> findOneByEmail( String email );
    public Optional<IUserProjection> findOneUserById( Long id );
    public Optional<UserEntity> findByCodeAuth_Code(String code);
    public Optional<IUserProjection> findOneUserByEmail( String email );
}
