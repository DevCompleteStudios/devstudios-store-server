package com.devstudios.store.devstudios_store_server.application.dtos.subscription;

import com.devstudios.store.devstudios_store_server.application.interfaces.projections.ISubscriptionPreviewProjection;




public class SubscriptionPreviewDto implements ISubscriptionPreviewProjection {

    private Long id;
    private Double price;
    private String name;
    private Long daysDuration;


    public SubscriptionPreviewDto(){}

    public SubscriptionPreviewDto(Long id, Double price, String name, Long daysDuration) {
        this.id = id;
        this.price = price;
        this.name = name;
        this.daysDuration = daysDuration;
    }



    @Override
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    @Override
    public Double getPrice() {
        return price;
    }
    public void setPrice(Double price) {
        this.price = price;
    }
    @Override
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    @Override
    public Long getDaysDuration() {
        return daysDuration;
    }
    public void setDaysDuration(Long daysDuration) {
        this.daysDuration = daysDuration;
    }

}

