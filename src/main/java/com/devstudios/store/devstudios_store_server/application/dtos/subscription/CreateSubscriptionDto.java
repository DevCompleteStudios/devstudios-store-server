package com.devstudios.store.devstudios_store_server.application.dtos.subscription;

import java.util.List;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;


public class CreateSubscriptionDto {

    @NotNull
    @NotEmpty
    @Size(min = 4, max = 50)
    private String name;

    @NotNull
    @NotEmpty
    @Size(min = 10, max = 250)
    private String description;

    @NotNull
    private List<String> content;

    @NotNull
    @Min(value = 1)
    @Max(value = 365)
    private Long daysDuration;

    @NotNull
    @Min(value = 2)
    @Max(value = 99)
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
