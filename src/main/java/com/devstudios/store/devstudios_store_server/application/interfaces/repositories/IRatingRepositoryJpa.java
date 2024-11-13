package com.devstudios.store.devstudios_store_server.application.interfaces.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.devstudios.store.devstudios_store_server.application.interfaces.projections.IRatingProjection;
import com.devstudios.store.devstudios_store_server.domain.entities.RatingEntity;




public interface IRatingRepositoryJpa extends JpaRepository<RatingEntity, Long> {
    public Page<IRatingProjection> findRatingByScript_Id( Long id, Pageable pageable );
}

