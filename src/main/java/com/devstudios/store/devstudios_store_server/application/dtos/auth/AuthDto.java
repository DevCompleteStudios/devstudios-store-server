package com.devstudios.store.devstudios_store_server.application.dtos.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;



public class AuthDto {

    @Email
    @NotNull
    private String email;

    @Size(max=50, min=4)
    @NotNull
    @NotEmpty
    private String password;


    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email.toLowerCase().trim();
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password.trim();
    }

}
