package com.devstudios.store.devstudios_store_server.infrastructure.repositories;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.devstudios.store.devstudios_store_server.application.interfaces.projections.IScriptPreviewProjection;
import com.devstudios.store.devstudios_store_server.application.interfaces.projections.IScriptProjection;
import com.devstudios.store.devstudios_store_server.application.interfaces.repositories.IScriptRepository;
import com.devstudios.store.devstudios_store_server.application.interfaces.repositories.IScriptRepositoryJpa;
import com.devstudios.store.devstudios_store_server.domain.entities.ScriptEntity;


@Repository
public class ScriptRepositoryImpl implements IScriptRepository {

    @Autowired
    IScriptRepositoryJpa repositoryJpa;


    @Override
    public ScriptEntity save(ScriptEntity scriptEntity) {
        return repositoryJpa.save(scriptEntity);
    }

    @Override
    public List<ScriptEntity> findAll() {
        return repositoryJpa.findAll();
    }


    @Override
    public Optional<IScriptProjection> findOneById(Long id) {
        return repositoryJpa.findOneScriptById(id);
    }

    @Override
    public Page<IScriptPreviewProjection> findAllScripts(Pageable pageable) {
        return repositoryJpa.getAll(pageable);
    }

    @Override
    public Optional<ScriptEntity> findById(Long id) {
        return repositoryJpa.findById(id);
    }



}
