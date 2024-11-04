package com.devstudios.store.devstudios_store_server.domain.entities;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;



@Entity
@Table(name="subscriptions_purchases")
public class SubscriptionPurchaseEntity extends EntityBase {

    private LocalDateTime dateExpired;
    private LocalDateTime dateAvailableForNewUser;
    private final String uuid = UUID.randomUUID().toString();


    @ManyToOne
    @JoinColumn(name = "subscription_id")
    private SubscriptionEntity subscription;

    @ManyToOne
    @JoinColumn(name="user_id")
    private UserEntity user;


    @PrePersist
    public void PrePersist(){
        this.dateExpired = LocalDateTime.now().plusDays(this.subscription.getDaysDuration());
    }


    public LocalDateTime getDateExpired() {
        return dateExpired;
    }
    public void setDateExpired(LocalDateTime dateExpired) {
        this.dateExpired = dateExpired;
    }
    public LocalDateTime getDateAvailableForNewUser() {
        return dateAvailableForNewUser;
    }
    public void setDateAvailableForNewUser(LocalDateTime dateAvailableForNewUser) {
        this.dateAvailableForNewUser = dateAvailableForNewUser;
    }
    public String getUuid() {
        return uuid;
    }
    public SubscriptionEntity getSubscription() {
        return subscription;
    }
    public void setSubscription(SubscriptionEntity subscription) {
        this.subscription = subscription;
    }
    public UserEntity getUser() {
        return user;
    }
    public void setUser(UserEntity user) {
        this.user = user;
    }

}
