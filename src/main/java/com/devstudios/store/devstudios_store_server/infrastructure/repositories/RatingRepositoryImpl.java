package com.devstudios.store.devstudios_store_server.infrastructure.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.devstudios.store.devstudios_store_server.application.interfaces.projections.IRatingProjection;
import com.devstudios.store.devstudios_store_server.application.interfaces.repositories.IRatingRepository;
import com.devstudios.store.devstudios_store_server.application.interfaces.repositories.IRatingRepositoryJpa;
import com.devstudios.store.devstudios_store_server.domain.entities.RatingEntity;



@Repository
public class RatingRepositoryImpl implements IRatingRepository {

    @Autowired
    IRatingRepositoryJpa repositoryJpa;


    @Override
    public Page<IRatingProjection> findRatingByScriptId(Long id, Pageable pageable) {
        return repositoryJpa.findRatingByScript_Id(id, pageable);
    }

    @Override
    public RatingEntity save(RatingEntity ratingEntity) {
        return repositoryJpa.save(ratingEntity);
    }


}


