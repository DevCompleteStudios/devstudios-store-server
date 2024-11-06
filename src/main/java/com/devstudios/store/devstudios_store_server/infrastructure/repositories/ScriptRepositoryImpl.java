package com.devstudios.store.devstudios_store_server.infrastructure.repositories;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.devstudios.store.devstudios_store_server.application.interfaces.projections.IScriptProjection;
import com.devstudios.store.devstudios_store_server.application.interfaces.repositories.IScriptRepository;
import com.devstudios.store.devstudios_store_server.application.interfaces.repositories.IScriptRepositoryJpa;
import com.devstudios.store.devstudios_store_server.domain.entities.ScriptEntity;




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
    public List<IScriptProjection> findAllScripts(Pageable pageable) {
        return repositoryJpa.findAllScripts(pageable);
    }

    @Override
    public Optional<IScriptProjection> findOneById(Long id) {
        return repositoryJpa.findOneById(id);
    }

}
