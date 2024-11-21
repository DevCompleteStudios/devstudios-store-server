package com.devstudios.store.devstudios_store_server.application.interfaces.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.devstudios.store.devstudios_store_server.domain.entities.SubscriptionPurchaseEntity;




public interface ISubscriptionPurchaseRepositoryJpa extends JpaRepository<SubscriptionPurchaseEntity, Long> {
    public Optional<SubscriptionPurchaseEntity> findByUuid(String uuid);
}
