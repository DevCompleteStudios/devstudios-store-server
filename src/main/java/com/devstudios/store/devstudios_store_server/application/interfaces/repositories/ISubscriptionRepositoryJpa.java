package com.devstudios.store.devstudios_store_server.application.interfaces.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.devstudios.store.devstudios_store_server.application.interfaces.projections.ISubscriptionPreviewProjection;
import com.devstudios.store.devstudios_store_server.domain.entities.SubscriptionEntity;



public interface ISubscriptionRepositoryJpa extends JpaRepository<SubscriptionEntity, Long> {
    @Query("SELECT s FROM SubscriptionEntity s WHERE s.isActive = true")
    public List<ISubscriptionPreviewProjection> findAllSubscriptions();
}

