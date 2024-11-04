package com.devstudios.store.devstudios_store_server.domain.entities;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;



@Entity
@Table(name = "scripts")
public class ScriptEntity extends EntityBase {

    private String name;
    private String description;
    private Double price;
    private Boolean isAvailableInSubscription;

    @OneToMany
    private List<RatingEntity> ratings = new ArrayList<>();

    @OneToMany(mappedBy="script")
    private List<ScriptPurchaseEntity> purchases = new ArrayList<>();


    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public Double getPrice() {
        return price;
    }
    public void setPrice(Double price) {
        this.price = price;
    }
    public Boolean getIsAvailableInSubscription() {
        return isAvailableInSubscription;
    }
    public void setIsAvailableInSubscription(Boolean isAvailableInSubscription) {
        this.isAvailableInSubscription = isAvailableInSubscription;
    }
    public List<RatingEntity> getRatings() {
        return ratings;
    }
    public void setRatings(List<RatingEntity> ratings) {
        this.ratings = ratings;
    }
    public List<ScriptPurchaseEntity> getPurchases() {
        return purchases;
    }
    public void setPurchases(List<ScriptPurchaseEntity> purchases) {
        this.purchases = purchases;
    }

}
