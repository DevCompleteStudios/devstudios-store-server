package com.devstudios.store.devstudios_store_server.application.dtos.script;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public class FreeAccesScriptDto {

    @Email
    @NotNull
    private String email;



    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email.trim().toLowerCase();
    }
}
