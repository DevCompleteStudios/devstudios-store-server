package com.devstudios.store.devstudios_store_server.application.interfaces.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.devstudios.store.devstudios_store_server.domain.entities.KeyEntity;



public interface IKeyRepositoryJpa extends JpaRepository<KeyEntity, Long> {
    public Optional<KeyEntity> findOneByValue(String value);

    @Query(value = "SELECT k.* FROM keys k " +
        "LEFT JOIN scripts_purchases sp1 ON k.id = sp1.key_id " +
        "LEFT JOIN subscriptions_purchases sp2 ON k.id = sp2.key_id " +
        "WHERE k.is_active = true " +
        "AND k.current_user_roblox_id = ?1 " +
        "AND (" +
        "    (sp1.id IS NOT NULL AND sp1.script_id = ?2) " +
        "    OR (sp2.id IS NOT NULL AND sp2.is_active = true)" +
        ") " +
        "LIMIT 1",
        nativeQuery = true)
    Optional<KeyEntity> findByRobloxIdAndScriptId(String robloxId, Long scriptId);
}
