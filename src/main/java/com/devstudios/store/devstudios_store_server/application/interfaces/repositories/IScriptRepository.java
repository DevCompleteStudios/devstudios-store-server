package com.devstudios.store.devstudios_store_server.application.interfaces.repositories;


import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.devstudios.store.devstudios_store_server.application.interfaces.projections.IScriptProjection;
import com.devstudios.store.devstudios_store_server.domain.entities.ScriptEntity;




public interface IScriptRepository {

    public ScriptEntity save( ScriptEntity scriptEntity );
    public List<ScriptEntity> findAll();
    public Page<IScriptProjection> findAllScripts( Pageable pageable );
    public Optional<IScriptProjection> findOneById( Long id );

}


