package com.devstudios.store.devstudios_store_server.application.dtos.subscription;

import java.util.List;



public class EditSubscriptionDto {

    private String name;
    private String description;
    private List<String> content;
    private Long daysDuration;
    private Double price;



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
    public List<String> getContent() {
        return content;
    }
    public void setContent(List<String> content) {
        this.content = content;
    }
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

}
