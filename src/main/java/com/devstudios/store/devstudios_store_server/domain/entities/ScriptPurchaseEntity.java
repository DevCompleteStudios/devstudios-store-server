package com.devstudios.store.devstudios_store_server.domain.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;



@Entity
@Table(name="scripts_purchases")
public class ScriptPurchaseEntity extends EntityBase {

    private String uuid;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "key_id")
    private KeyEntity key;

    @ManyToOne
    @JoinColumn(name="script_id")
    private ScriptEntity script;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @OneToOne(mappedBy = "purchase", cascade = CascadeType.PERSIST)
    private RatingEntity rating;

    private Double amount;



    public String getUuid() {
        return uuid;
    }
    
    public ScriptEntity getScript() {
        return script;
    }
    public void setScript(ScriptEntity script) {
        this.script = script;
    }
    public UserEntity getUser() {
        return user;
    }
    public void setUser(UserEntity user) {
        this.user = user;
    }
    public KeyEntity getKey() {
        return key;
    }
    public void setKey(KeyEntity key) {
        this.key = key;
    }
    public RatingEntity getRating() {
        return rating;
    }
    public void setRating(RatingEntity rating) {
        this.rating = rating;
    }
    public Double getAmount() {
        return amount;
    }
    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

}
