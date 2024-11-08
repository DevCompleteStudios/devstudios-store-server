package com.devstudios.store.devstudios_store_server.domain.entities;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;



@Entity
@Table(name="subscriptions")
public class SubscriptionEntity extends EntityBase {

    private Double price;
    private String description;
    private String name;
    private Long daysDuration = 30L;
    private List<String> contentList;

    @OneToMany(mappedBy="subscription")
    private List<SubscriptionPurchaseEntity> subscriptionsPurchases = new ArrayList<>();


    public Long getDaysDuration() {
        return daysDuration;
    }
    public void setDaysDuration(Long daysDuration) {
        this.daysDuration = daysDuration;
    }
    public Double getPrice() {
        return price;
    }
    public void setPrice(Double price) {
        this.price = price;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public List<SubscriptionPurchaseEntity> getSubscriptionsPurchases() {
        return subscriptionsPurchases;
    }
    public void setSubscriptionsPurchases(List<SubscriptionPurchaseEntity> subscriptionsPurchases) {
        this.subscriptionsPurchases = subscriptionsPurchases;
    }
    public List<String> getContentList() {
        return contentList;
    }
    public void setContentList(List<String> contentList) {
        this.contentList = contentList;
    }

}
