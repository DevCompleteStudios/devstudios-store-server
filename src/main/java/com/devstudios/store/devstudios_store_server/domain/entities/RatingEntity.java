package com.devstudios.store.devstudios_store_server.domain.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;


@Entity
@Table(name = "ratings")
public class RatingEntity extends EntityBase {

    private int stars;
    private String content;

    @ManyToOne
    private UserEntity user;

    @Column(unique=true)
    @OneToOne
    private ScriptPurchaseEntity purchase;



    public int getStars() {
        return stars;
    }
    public void setStars(int stars) {
        this.stars = stars;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public UserEntity getUser() {
        return user;
    }
    public void setUser(UserEntity user) {
        this.user = user;
    }
    public ScriptPurchaseEntity getPurchase() {
        return purchase;
    }
    public void setPurchase(ScriptPurchaseEntity purchase) {
        this.purchase = purchase;
    }


}
