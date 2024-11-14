package com.devstudios.store.devstudios_store_server.application.interfaces.repositories;

import java.util.List;
import java.util.Optional;

import com.devstudios.store.devstudios_store_server.application.interfaces.projections.ISubscriptionProjection;
import com.devstudios.store.devstudios_store_server.domain.entities.SubscriptionEntity;



public interface ISubscriptionRepository {
    public SubscriptionEntity save(SubscriptionEntity subscriptionEntity);
    public List<ISubscriptionProjection> findAllSubscriptions();
    public Optional<SubscriptionEntity> findById( Long id );
}
