package com.devstudios.store.devstudios_store_server.application.interfaces.repositories;

import java.util.Optional;

import com.devstudios.store.devstudios_store_server.domain.entities.SubscriptionPurchaseEntity;

public interface ISubscriptionPurchaseRepository {
    Optional<SubscriptionPurchaseEntity> findByUuid( String orderId );
    SubscriptionPurchaseEntity save(SubscriptionPurchaseEntity entity);
}
