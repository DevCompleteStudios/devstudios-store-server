package com.devstudios.store.devstudios_store_server.application.interfaces.services;

import java.util.List;

import com.devstudios.store.devstudios_store_server.domain.entities.RoleEntity;



public interface IJwtService {

    public String createJwt( List<RoleEntity> roles, String email );

}
