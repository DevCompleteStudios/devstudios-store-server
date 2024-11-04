package com.devstudios.store.devstudios_store_server.domain.entities;

import java.util.UUID;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;



@Entity
@Table(name="keys")
public class KeyEntity extends EntityBase {

    private final String value = UUID.randomUUID().toString();
    private String currentUserRobloxId;

    @OneToOne(mappedBy="key", cascade = CascadeType.ALL, orphanRemoval = true)
    private ScriptPurchaseEntity scriptPurchase;

    @OneToOne(mappedBy="key", cascade = CascadeType.ALL, orphanRemoval = true)
    private SubscriptionPurchaseEntity subscriptionPurchase;


    public KeyEntity(){}

    public KeyEntity(ScriptPurchaseEntity scriptPurchase){
        this.scriptPurchase = scriptPurchase;
    }

    public KeyEntity(SubscriptionPurchaseEntity subscriptionPurchase){
        this.subscriptionPurchase = subscriptionPurchase;
    }



    public String getCurrentUserRobloxId() {
        return currentUserRobloxId;
    }
    public void setCurrentUserRobloxId(String currentUserRobloxId) {
        this.currentUserRobloxId = currentUserRobloxId;
    }
    public String getValue() {
        return value;
    }
    public ScriptPurchaseEntity getScriptPurchase() {
        return scriptPurchase;
    }
    public SubscriptionPurchaseEntity getSubscriptionPurchase() {
        return subscriptionPurchase;
    }




}
