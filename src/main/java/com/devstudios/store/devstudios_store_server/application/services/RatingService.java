package com.devstudios.store.devstudios_store_server.application.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.devstudios.store.devstudios_store_server.application.dtos.rating.AddRatingDto;
import com.devstudios.store.devstudios_store_server.application.dtos.shared.PaginationDto;
import com.devstudios.store.devstudios_store_server.application.dtos.shared.ResponseDto;
import com.devstudios.store.devstudios_store_server.application.dtos.shared.ResponsePaginationDto;
import com.devstudios.store.devstudios_store_server.application.interfaces.projections.IRatingProjection;
import com.devstudios.store.devstudios_store_server.application.interfaces.repositories.IRatingRepository;
import com.devstudios.store.devstudios_store_server.application.interfaces.repositories.IScriptPurchaseRepository;
import com.devstudios.store.devstudios_store_server.domain.entities.RatingEntity;
import com.devstudios.store.devstudios_store_server.domain.entities.ScriptPurchaseEntity;
import com.devstudios.store.devstudios_store_server.infrastructure.CustomExceptions.CustomException;



@Service
public class RatingService {

    @Autowired
    IRatingRepository repository;
    @Autowired
    IScriptPurchaseRepository scriptPurchaseRepository;



    public ResponseDto<Boolean> addComent( Long scriptId, AddRatingDto addRatingDto ){
        ScriptPurchaseEntity scriptPurchaseEntity = scriptPurchaseRepository.findByUuid(addRatingDto.getOrderId())
            .orElseThrow( () -> CustomException.badRequestException("Order id is not valod"));

        if( scriptPurchaseEntity.getScript().getId() != scriptId ){
            throw CustomException.badRequestException("This purchase order is not valid for this");
        }else if(scriptPurchaseEntity.getRating() != null){
            throw CustomException.badRequestException("A comment has already been registered with this purchase");
        }

        RatingEntity rating = new RatingEntity();

        rating.setContent(addRatingDto.getContent());
        rating.setStars(addRatingDto.getStars());
        rating.setPurchase(scriptPurchaseEntity);
        rating.setScript(scriptPurchaseEntity.getScript());

        scriptPurchaseEntity.setRating(rating);
        scriptPurchaseRepository.save(scriptPurchaseEntity);

        return new ResponseDto<>(null, 201, true);
    }

    public ResponsePaginationDto<List<IRatingProjection>> getAllComentsByScriptId( Long scriptId, PaginationDto paginationDto ){
        Pageable page = PageRequest.of(paginationDto.getPage(), paginationDto.getElements());
        Page<IRatingProjection> elements = repository.findRatingByScriptId(scriptId, page);

        return new ResponsePaginationDto<List<IRatingProjection>>(elements.getTotalPages(), elements.getTotalElements(), null, 200, elements.getContent());
    }


}
