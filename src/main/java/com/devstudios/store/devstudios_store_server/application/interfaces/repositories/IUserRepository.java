package com.devstudios.store.devstudios_store_server.application.interfaces.repositories;

import com.devstudios.store.devstudios_store_server.domain.entities.UserEntity;





public interface IUserRepository {

    public UserEntity save(UserEntity user);

}
