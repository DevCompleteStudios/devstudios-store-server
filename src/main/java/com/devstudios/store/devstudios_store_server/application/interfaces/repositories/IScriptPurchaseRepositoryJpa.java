package com.devstudios.store.devstudios_store_server.application.interfaces.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.devstudios.store.devstudios_store_server.domain.entities.ScriptPurchaseEntity;



public interface IScriptPurchaseRepositoryJpa extends JpaRepository<ScriptPurchaseEntity, Long> {
    public Optional<ScriptPurchaseEntity> findByUuid(String uuid);
}
