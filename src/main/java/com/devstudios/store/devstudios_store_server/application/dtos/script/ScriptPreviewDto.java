package com.devstudios.store.devstudios_store_server.application.dtos.script;


import com.devstudios.store.devstudios_store_server.application.interfaces.enums.ScriptMethodPayment;
import com.devstudios.store.devstudios_store_server.application.interfaces.projections.IScriptPreviewProjection;



public class ScriptPreviewDto implements IScriptPreviewProjection {

    private Long id;
    private String name;
    private String description;
    private Double price;
    private ScriptMethodPayment methodPayment;
    private Boolean isActive;
    private String image;


    public ScriptPreviewDto(){}

    public ScriptPreviewDto(Long id, String name, String description, Double price) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
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
    public ScriptMethodPayment getMethodPayment() {
        return methodPayment;
    }
    public void setMethodPayment(ScriptMethodPayment methodPayment) {
        this.methodPayment = methodPayment;
    }
    @Override
    public Boolean getIsActive() {
        return isActive;
    }
    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }
    @Override
    public String getImage() {
        return image;
    }
    public void setImage(String image) {
        this.image = image;
    }



}
