package com.devstudios.store.devstudios_store_server.application.interfaces.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.devstudios.store.devstudios_store_server.domain.entities.KeyEntity;



public interface IKeyRepositoryJpa extends JpaRepository<KeyEntity, Long> {
    public Optional<KeyEntity> findOneByValue(String value);
    public Optional<KeyEntity> findFirstByCurrentUserRobloxIdAndScriptPurchase_Script_Id(String robloxId, Long scriptId);
}
