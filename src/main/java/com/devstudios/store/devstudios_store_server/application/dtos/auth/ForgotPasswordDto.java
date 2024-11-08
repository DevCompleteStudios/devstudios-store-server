package com.devstudios.store.devstudios_store_server.application.dtos.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public class ForgotPasswordDto {

    @NotNull
    @Email
    private String email;



    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

}
