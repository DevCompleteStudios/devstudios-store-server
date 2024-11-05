package com.devstudios.store.devstudios_store_server.application.interfaces.projections;

import java.util.List;

import com.devstudios.store.devstudios_store_server.domain.entities.RoleEntity;



public interface IUserPreviewProjection extends IProjection {

    public List<RoleEntity> getRoles();

}
