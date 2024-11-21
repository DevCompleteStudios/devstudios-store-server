package com.devstudios.store.devstudios_store_server.infrastructure.repositories;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.devstudios.store.devstudios_store_server.application.interfaces.repositories.ISubscriptionPurchaseRepository;
import com.devstudios.store.devstudios_store_server.application.interfaces.repositories.ISubscriptionPurchaseRepositoryJpa;
import com.devstudios.store.devstudios_store_server.domain.entities.SubscriptionPurchaseEntity;


@Repository
public class ISubscritionpurchaseRepositoryImpl implements ISubscriptionPurchaseRepository {

    @Autowired
    ISubscriptionPurchaseRepositoryJpa repository;


    @Override
    public Optional<SubscriptionPurchaseEntity> findByUuid(String orderId) {
        return repository.findByUuid(orderId);
    }

    @Override
    public SubscriptionPurchaseEntity save(SubscriptionPurchaseEntity entity) {
        return repository.save(entity);
    }

}
