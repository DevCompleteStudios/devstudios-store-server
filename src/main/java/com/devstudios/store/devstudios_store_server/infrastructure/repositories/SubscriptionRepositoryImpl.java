package com.devstudios.store.devstudios_store_server.infrastructure.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.devstudios.store.devstudios_store_server.application.interfaces.projections.ISubscriptionProjection;
import com.devstudios.store.devstudios_store_server.application.interfaces.repositories.ISubscriptionRepository;
import com.devstudios.store.devstudios_store_server.application.interfaces.repositories.ISubscriptionRepositoryJpa;
import com.devstudios.store.devstudios_store_server.domain.entities.SubscriptionEntity;



@Repository
public class SubscriptionRepositoryImpl implements ISubscriptionRepository {

    @Autowired
    ISubscriptionRepositoryJpa repositoryJpa;



    @Override
    public SubscriptionEntity save(SubscriptionEntity subscriptionEntity) {
        return repositoryJpa.save(subscriptionEntity);
    }

    @Override
    public List<ISubscriptionProjection> findAllSubscriptions() {
        return repositoryJpa.findAllSubscriptions();
    }

    @Override
    public Optional<SubscriptionEntity> findById(Long id) {
        return repositoryJpa.findById(id);
    }

}
