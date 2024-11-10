package com.devstudios.store.devstudios_store_server.application.interfaces.repositories;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.devstudios.store.devstudios_store_server.application.interfaces.projections.IScriptPreviewProjection;
import com.devstudios.store.devstudios_store_server.application.interfaces.projections.IScriptProjection;
import com.devstudios.store.devstudios_store_server.domain.entities.ScriptEntity;



public interface IScriptRepositoryJpa extends JpaRepository<ScriptEntity, Long> {
    @Query("SELECT s FROM ScriptEntity s")
    public Page<IScriptPreviewProjection> getAll(Pageable pageable);
    public Optional<IScriptProjection> findOneScriptById( Long id );
}


