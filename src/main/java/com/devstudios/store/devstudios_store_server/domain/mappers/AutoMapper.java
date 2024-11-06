package com.devstudios.store.devstudios_store_server.domain.mappers;

import java.util.List;

import org.springframework.stereotype.Component;

import com.devstudios.store.devstudios_store_server.application.dtos.key.KeyDto;
import com.devstudios.store.devstudios_store_server.application.dtos.subscription.SubscriptionPreviewDto;
import com.devstudios.store.devstudios_store_server.application.dtos.subscription.SubscriptionPurchaseDto;
import com.devstudios.store.devstudios_store_server.application.dtos.user.UserDto;
import com.devstudios.store.devstudios_store_server.application.interfaces.projections.IKeyProjection;
import com.devstudios.store.devstudios_store_server.application.interfaces.projections.ISubscriptionPreviewProjection;
import com.devstudios.store.devstudios_store_server.application.interfaces.projections.ISubscriptionPurchaseProjection;
import com.devstudios.store.devstudios_store_server.application.interfaces.projections.IUserProjection;
import com.devstudios.store.devstudios_store_server.domain.entities.KeyEntity;
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

        user.setSubscriptionsPurchases(subs);
        user.setUpdatedAt(userEntity.getUpdatedAt());

        return user;
    }

    ISubscriptionPurchaseProjection subscriptionPurchaseToProjection( SubscriptionPurchaseEntity entity ){
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
            suscriptionEntityToProjectionPreview(entity.getSubscription())
        );

        dto.setUpdatedAt(entity.getUpdatedAt());
        dto.setUuid(entity.getUuid());

        return dto;
    }

    ISubscriptionPreviewProjection suscriptionEntityToProjectionPreview( SubscriptionEntity entity ){
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

}
