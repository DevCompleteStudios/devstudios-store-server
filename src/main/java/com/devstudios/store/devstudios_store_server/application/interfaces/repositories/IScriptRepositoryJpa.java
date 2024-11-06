package com.devstudios.store.devstudios_store_server.application.interfaces.repositories;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.devstudios.store.devstudios_store_server.application.interfaces.projections.IScriptProjection;
import com.devstudios.store.devstudios_store_server.domain.entities.ScriptEntity;




public interface IScriptRepositoryJpa extends JpaRepository<ScriptEntity, Long> {
    public Optional<ScriptEntity> findOneById();
    public List<IScriptProjection> findAllScripts( Pageable pageable );
    public Optional<IScriptProjection> findOneById( Long id );
}


