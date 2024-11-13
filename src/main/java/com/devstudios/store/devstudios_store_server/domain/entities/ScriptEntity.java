package com.devstudios.store.devstudios_store_server.domain.entities;

import java.util.ArrayList;
import java.util.List;

import com.devstudios.store.devstudios_store_server.application.interfaces.enums.ScriptMethodPayment;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;



@Entity
@Table(name = "scripts")
public class ScriptEntity extends EntityBase {

    @Column(unique=true)
    private String name;
    private String description;
    private Double price;

    private String youtubeLink;
    private String image;

    @Enumerated(EnumType.STRING)
    private ScriptMethodPayment methodPayment;

    @OneToMany(mappedBy = "script", cascade = CascadeType.ALL, orphanRemoval = true)
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
    public ScriptMethodPayment getMethodPayment() {
        return methodPayment;
    }
    public void setMethodPayment(ScriptMethodPayment methodPayment) {
        this.methodPayment = methodPayment;
    }
    public String getYoutubeLink() {
        return youtubeLink;
    }
    public void setYoutubeLink(String youtubeLink) {
        this.youtubeLink = youtubeLink;
    }
    public String getImage() {
        return image;
    }
    public void setImage(String image) {
        this.image = image;
    }

}
