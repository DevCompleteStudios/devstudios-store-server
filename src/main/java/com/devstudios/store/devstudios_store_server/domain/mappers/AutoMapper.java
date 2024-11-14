package com.devstudios.store.devstudios_store_server.domain.mappers;

import java.util.List;

import org.springframework.stereotype.Component;

import com.devstudios.store.devstudios_store_server.application.dtos.key.KeyDto;
import com.devstudios.store.devstudios_store_server.application.dtos.rating.RatingDto;
import com.devstudios.store.devstudios_store_server.application.dtos.script.ScriptPreviewDto;
import com.devstudios.store.devstudios_store_server.application.dtos.script.ScriptPurchaseDto;
import com.devstudios.store.devstudios_store_server.application.dtos.subscription.SubscriptionPreviewDto;
import com.devstudios.store.devstudios_store_server.application.dtos.subscription.SubscriptionPurchaseDto;
import com.devstudios.store.devstudios_store_server.application.dtos.user.UserDto;
import com.devstudios.store.devstudios_store_server.application.interfaces.projections.IKeyProjection;
import com.devstudios.store.devstudios_store_server.application.interfaces.projections.IRatingProjection;
import com.devstudios.store.devstudios_store_server.application.interfaces.projections.IScriptPreviewProjection;
import com.devstudios.store.devstudios_store_server.application.interfaces.projections.IScriptPurchaseProjection;
import com.devstudios.store.devstudios_store_server.application.interfaces.projections.ISubscriptionPreviewProjection;
import com.devstudios.store.devstudios_store_server.application.interfaces.projections.ISubscriptionPurchaseProjection;
import com.devstudios.store.devstudios_store_server.application.interfaces.projections.IUserProjection;
import com.devstudios.store.devstudios_store_server.domain.entities.KeyEntity;
import com.devstudios.store.devstudios_store_server.domain.entities.RatingEntity;
import com.devstudios.store.devstudios_store_server.domain.entities.ScriptEntity;
import com.devstudios.store.devstudios_store_server.domain.entities.ScriptPurchaseEntity;
import com.devstudios.store.devstudios_store_server.domain.entities.SubscriptionEntity;
import com.devstudios.store.devstudios_store_server.domain.entities.SubscriptionPurchaseEntity;
import com.devstudios.store.devstudios_store_server.domain.entities.UserEntity;



@Component
public class AutoMapper {


    public IUserProjection userEntityToProjection( UserEntity userEntity ){
        if( userEntity == null ) return null;
        UserDto user = new UserDto();

        user.setCreatedAt(userEntity.getCreatedAt());
        user.setEmail(userEntity.getEmail());
        user.setId(userEntity.getId());
        user.setIsActive(userEntity.getIsActive());
        user.setRoles(userEntity.getRoles());

        List<ISubscriptionPurchaseProjection> subs = userEntity.getSubscriptionPurchases().stream()
            .map( s -> subscriptionPurchaseToProjection(s) ).toList();
        List<IScriptPurchaseProjection> scripts = userEntity.getScriptsPurchases().stream()
            .map( s -> scriptPurchaseToProjection(s) ).toList();

        user.setSubscriptionsPurchases(subs);
        user.setScriptsPurchases(scripts);
        user.setUpdatedAt(userEntity.getUpdatedAt());

        return user;
    }

    public IScriptPreviewProjection scriptToProjectionPreview( ScriptEntity entity ){
        if( entity == null ) return null;
        ScriptPreviewDto dto = new ScriptPreviewDto();

        dto.setDescription(entity.getDescription());
        dto.setId(entity.getId());
        dto.setMethodPayment(entity.getMethodPayment());
        dto.setName(entity.getName());
        dto.setPrice(entity.getPrice());
        dto.setIsActive(entity.getIsActive());
        dto.setImage(entity.getImage());

        return dto;
    }

    public IScriptPurchaseProjection scriptPurchaseToProjection( ScriptPurchaseEntity entity ){
        if( entity == null ) return null;
        ScriptPurchaseDto dto = new ScriptPurchaseDto();

        dto.setKey(
            keyEntityToProjection(entity.getKey())
        );
        dto.setScript(
            scriptToProjectionPreview(entity.getScript())
        );
        dto.setUuid(entity.getUuid());
        dto.setAmount(entity.getAmount());
        dto.setIsActive(entity.getIsActive());

        return dto;
    }

    public ISubscriptionPurchaseProjection subscriptionPurchaseToProjection( SubscriptionPurchaseEntity entity ){
        if( entity == null ) return null;
        SubscriptionPurchaseDto dto = new SubscriptionPurchaseDto();

        dto.setCreatedAt(entity.getCreatedAt());
        dto.setDateAvailableForNewUser(entity.getDateAvailableForNewUser());
        dto.setDateExpired(entity.getDateExpired());
        dto.setId(entity.getId());
        dto.setIsActive(entity.getIsActive());
        dto.setKey(
            keyEntityToProjection(entity.getKey())
        );
        dto.setSubscription(
            subscriptionEntityToProjectionPreview(entity.getSubscription())
        );
        dto.setAmount(entity.getAmount());

        dto.setUpdatedAt(entity.getUpdatedAt());
        dto.setUuid(entity.getUuid());

        return dto;
    }

    public ISubscriptionPreviewProjection subscriptionEntityToProjectionPreview( SubscriptionEntity entity ){
        if( entity == null ) return null;
        SubscriptionPreviewDto dto = new SubscriptionPreviewDto();

        dto.setDaysDuration(entity.getDaysDuration());
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setPrice(entity.getPrice());

        return dto;
    }

    public IKeyProjection keyEntityToProjection( KeyEntity keyEntity ){
        if( keyEntity == null ) return null;
        KeyDto key = new KeyDto();

        key.setValue(keyEntity.getValue());
        key.setCurrentUserRobloxId(keyEntity.getCurrentUserRobloxId());
        key.setId(keyEntity.getId());

        return key;
    }

    public IRatingProjection ratingEntityToProjection( RatingEntity entity ){
        if( entity == null ) return null;
        RatingDto rating = new RatingDto();

        rating.setContent(entity.getContent());
        rating.setCreatedAt(entity.getCreatedAt());
        rating.setId(entity.getId());
        rating.setIsActive(entity.getIsActive());
        rating.setStars(entity.getStars());
        rating.setUpdatedAt(entity.getUpdatedAt());

        return rating;
    }

}
