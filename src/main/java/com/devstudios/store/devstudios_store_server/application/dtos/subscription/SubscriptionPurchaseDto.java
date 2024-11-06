package com.devstudios.store.devstudios_store_server.application.dtos.subscription;

import java.time.LocalDateTime;

import com.devstudios.store.devstudios_store_server.application.interfaces.projections.IKeyProjection;
import com.devstudios.store.devstudios_store_server.application.interfaces.projections.ISubscriptionPreviewProjection;
import com.devstudios.store.devstudios_store_server.application.interfaces.projections.ISubscriptionPurchaseProjection;




public class SubscriptionPurchaseDto implements ISubscriptionPurchaseProjection {

    private LocalDateTime dateExpired;
    private LocalDateTime dateAvailableForNewUser;
    private String uuid;
    private IKeyProjection key;
    private ISubscriptionPreviewProjection subscription;
    private Long id;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Boolean isActive;


    public SubscriptionPurchaseDto(){}

    public SubscriptionPurchaseDto(LocalDateTime dateExpired, LocalDateTime dateAvailableForNewUser, String uuid,
            IKeyProjection key, ISubscriptionPreviewProjection subscription, Long id, LocalDateTime createdAt,
            LocalDateTime updatedAt, Boolean isActive) {
        this.dateExpired = dateExpired;
        this.dateAvailableForNewUser = dateAvailableForNewUser;
        this.uuid = uuid;
        this.key = key;
        this.subscription = subscription;
        this.id = id;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.isActive = isActive;
    }



    @Override
    public LocalDateTime getDateExpired() {
        return dateExpired;
    }
    public void setDateExpired(LocalDateTime dateExpired) {
        this.dateExpired = dateExpired;
    }
    @Override
    public LocalDateTime getDateAvailableForNewUser() {
        return dateAvailableForNewUser;
    }
    public void setDateAvailableForNewUser(LocalDateTime dateAvailableForNewUser) {
        this.dateAvailableForNewUser = dateAvailableForNewUser;
    }
    @Override
    public String getUuid() {
        return uuid;
    }
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
    @Override
    public IKeyProjection getKey() {
        return key;
    }
    public void setKey(IKeyProjection key) {
        this.key = key;
    }
    @Override
    public ISubscriptionPreviewProjection getSubscription() {
        return subscription;
    }
    public void setSubscription(ISubscriptionPreviewProjection subscription) {
        this.subscription = subscription;
    }
    @Override
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    @Override
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    @Override
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
    @Override
    public Boolean getIsActive() {
        return isActive;
    }
    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }


}
