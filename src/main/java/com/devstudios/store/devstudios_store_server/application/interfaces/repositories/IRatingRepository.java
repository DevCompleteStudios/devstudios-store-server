package com.devstudios.store.devstudios_store_server.application.interfaces.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.devstudios.store.devstudios_store_server.application.interfaces.projections.IRatingProjection;
import com.devstudios.store.devstudios_store_server.domain.entities.RatingEntity;




@Repository
public interface IRatingRepository {

    public Page<IRatingProjection> findRatingByScriptId( Long id, Pageable pageable );
    public RatingEntity save(RatingEntity ratingEntity);

}


