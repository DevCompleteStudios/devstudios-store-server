package com.devstudios.store.devstudios_store_server.application.dtos.auth;

import org.hibernate.validator.constraints.UUID;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class VerifyAccesScriptDto {

    @UUID(message = "Key is not valid")
    private String key;

    @NotNull
    @NotBlank
    private String robloxId;


    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getRobloxId() {
        return robloxId;
    }

    public void setRobloxId(String robloxId) {
        this.robloxId = robloxId;
    }


}


