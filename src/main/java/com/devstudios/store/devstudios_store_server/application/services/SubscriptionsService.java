package com.devstudios.store.devstudios_store_server.application.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.devstudios.store.devstudios_store_server.application.dtos.shared.ResponseDto;
import com.devstudios.store.devstudios_store_server.application.dtos.subscription.CreateSubscriptionDto;
import com.devstudios.store.devstudios_store_server.application.dtos.subscription.EditSubscriptionDto;
import com.devstudios.store.devstudios_store_server.application.interfaces.projections.ISubscriptionPreviewProjection;
import com.devstudios.store.devstudios_store_server.application.interfaces.repositories.ISubscriptionRepository;
import com.devstudios.store.devstudios_store_server.domain.entities.SubscriptionEntity;
import com.devstudios.store.devstudios_store_server.domain.mappers.AutoMapper;
import com.devstudios.store.devstudios_store_server.infrastructure.CustomExceptions.CustomException;




@Service
public class SubscriptionsService {

    @Autowired
    ISubscriptionRepository repository;
    @Autowired
    AutoMapper mapper;



    public ResponseDto<List<ISubscriptionPreviewProjection>> findAll(){
        List<ISubscriptionPreviewProjection> allElements = repository.findAllSubscriptions();
        return new ResponseDto<>(null, 200, allElements);
    }

    public ResponseDto<SubscriptionEntity> create( CreateSubscriptionDto createSubscriptionDto ){
        SubscriptionEntity newSub = new SubscriptionEntity();

        newSub.setContent(createSubscriptionDto.getContent());
        newSub.setDaysDuration(createSubscriptionDto.getDaysDuration());
        newSub.setDescription(createSubscriptionDto.getDescription());
        newSub.setName(createSubscriptionDto.getName());
        newSub.setPrice(createSubscriptionDto.getPrice());

        repository.save(newSub);

        return new ResponseDto<>(null, 201, newSub);
    }

    public ResponseDto<Boolean> edit( EditSubscriptionDto editSubscriptionDto, Long id ){
        SubscriptionEntity sub = repository.findById(id)
            .orElseThrow( () -> CustomException.notFoundException("SUbscription not exist"));

        if( editSubscriptionDto.getContent() != null ) sub.setContent(editSubscriptionDto.getContent());
        if( editSubscriptionDto.getDaysDuration() != null ) sub.setDaysDuration(editSubscriptionDto.getDaysDuration());
        if( editSubscriptionDto.getDescription() != null ) sub.setDescription(editSubscriptionDto.getDescription());
        if( editSubscriptionDto.getName() != null ) sub.setName(editSubscriptionDto.getName());
        if( editSubscriptionDto.getPrice() != null ) sub.setPrice(editSubscriptionDto.getPrice());

        repository.save(sub);
        return new ResponseDto<>(null, 200, true);
    }

    public ResponseDto<Boolean> delete( Long id ){
        SubscriptionEntity entity = repository.findById(id)
            .orElseThrow( () -> CustomException.notFoundException("Subscription not exists"));

        entity.setIsActive(false);
        repository.save(entity);

        return new ResponseDto<>(null, 200, true);
    }

}
