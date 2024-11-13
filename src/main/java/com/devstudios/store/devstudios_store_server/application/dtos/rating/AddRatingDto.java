package com.devstudios.store.devstudios_store_server.application.dtos.rating;

import org.hibernate.validator.constraints.UUID;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;



public class AddRatingDto {

    @Size(max = 250, min = 4)
    @NotNull
    private String content;

    @Min(value = 1)
    @Max(value = 5)
    @NotNull
    private int stars;

    @UUID
    @NotNull
    private String orderId;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getStars() {
        return stars;
    }

    public void setStars(int stars) {
        this.stars = stars;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }



    
}
