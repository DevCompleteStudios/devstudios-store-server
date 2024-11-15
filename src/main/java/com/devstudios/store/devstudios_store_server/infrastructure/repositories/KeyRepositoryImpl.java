package com.devstudios.store.devstudios_store_server.infrastructure.repositories;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.devstudios.store.devstudios_store_server.application.interfaces.repositories.IKeyRepository;
import com.devstudios.store.devstudios_store_server.application.interfaces.repositories.IKeyRepositoryJpa;
import com.devstudios.store.devstudios_store_server.domain.entities.KeyEntity;




@Repository
public class KeyRepositoryImpl implements IKeyRepository {

    @Autowired
    private IKeyRepositoryJpa repositoryJpa;


    @Override
    public KeyEntity save(KeyEntity keyEntity) {
        return repositoryJpa.save(keyEntity);
    }

    @Override
    public Optional<KeyEntity> findByValue(String value) {
        return repositoryJpa.findOneByValue(value);
    }

}
