package com.devstudios.store.devstudios_store_server.application.dtos.script;

import com.devstudios.store.devstudios_store_server.application.interfaces.projections.IScriptPreviewProjection;



public class ScriptPreviewDto implements IScriptPreviewProjection {

    private Long id;
    private String name;
    private String description;
    private Double price;
    private Boolean isAvailableInSubscription;


    public ScriptPreviewDto(){}

    public ScriptPreviewDto(Long id, String name, String description, Double price, Boolean isAvailableInSubscription) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.isAvailableInSubscription = isAvailableInSubscription;
    }




    @Override
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    @Override
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    @Override
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    @Override
    public Double getPrice() {
        return price;
    }
    public void setPrice(Double price) {
        this.price = price;
    }
    @Override
    public Boolean getIsAvailableInSubscription() {
        return isAvailableInSubscription;
    }
    public void setIsAvailableInSubscription(Boolean isAvailableInSubscription) {
        this.isAvailableInSubscription = isAvailableInSubscription;
    }

}
