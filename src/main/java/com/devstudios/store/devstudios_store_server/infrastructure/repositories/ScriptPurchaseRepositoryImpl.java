package com.devstudios.store.devstudios_store_server.infrastructure.repositories;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.devstudios.store.devstudios_store_server.application.interfaces.repositories.IScriptPurchaseRepository;
import com.devstudios.store.devstudios_store_server.application.interfaces.repositories.IScriptPurchaseRepositoryJpa;
import com.devstudios.store.devstudios_store_server.domain.entities.ScriptPurchaseEntity;

@Repository
public class ScriptPurchaseRepositoryImpl implements IScriptPurchaseRepository {

    @Autowired
    IScriptPurchaseRepositoryJpa repositoryJpa;


    @Override
    public Optional<ScriptPurchaseEntity> findByUuid(String uuid) {
        return repositoryJpa.findByUuid(uuid);
    }

    @Override
    public ScriptPurchaseEntity save(ScriptPurchaseEntity scriptPurchaseEntity) {
        return repositoryJpa.save(scriptPurchaseEntity);
    }

}
