package com.devstudios.store.devstudios_store_server.application.dtos.script;

import com.devstudios.store.devstudios_store_server.application.interfaces.enums.ScriptMethodPayment;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;




public class CreateScriptDto {

    @Size(min = 4, max = 70)
    @NotNull
    @NotBlank
    private String name;

    @Size(min = 10, max = 250)
    @NotNull
    @NotBlank
    private String description;

    @Min(value = 1)
    @Max(99)
    private Double price;

    @Enumerated(EnumType.STRING)
    @NotNull
    private ScriptMethodPayment methodPayment;


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
    public ScriptMethodPayment getMethodPayment() {
        return methodPayment;
    }
    public void setMethodPayment(ScriptMethodPayment methodPayment) {
        this.methodPayment = methodPayment;
    }

}
