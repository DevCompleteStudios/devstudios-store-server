package com.devstudios.store.devstudios_store_server.application.interfaces.repositories;

import org.springframework.data.repository.CrudRepository;

import com.devstudios.store.devstudios_store_server.domain.entities.UserEntity;



public interface IUserRepositoryJpa extends CrudRepository<UserEntity, Long> {}
