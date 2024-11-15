package com.devstudios.store.devstudios_store_server.application.interfaces.repositories;

import java.util.Optional;

import com.devstudios.store.devstudios_store_server.domain.entities.KeyEntity;




public interface IKeyRepository {
    public KeyEntity save(KeyEntity keyEntity);
    public Optional<KeyEntity> findByValue(String value);
}
