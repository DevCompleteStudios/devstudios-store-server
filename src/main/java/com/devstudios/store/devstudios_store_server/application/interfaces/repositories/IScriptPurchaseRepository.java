package com.devstudios.store.devstudios_store_server.application.interfaces.repositories;

import java.util.Optional;

import com.devstudios.store.devstudios_store_server.domain.entities.ScriptPurchaseEntity;




public interface IScriptPurchaseRepository {

    public Optional<ScriptPurchaseEntity> findByUuid( String uuid );
    public ScriptPurchaseEntity save(ScriptPurchaseEntity scriptPurchaseEntity);

}
