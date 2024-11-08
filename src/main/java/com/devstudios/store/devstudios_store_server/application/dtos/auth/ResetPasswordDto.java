package com.devstudios.store.devstudios_store_server.application.dtos.auth;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class ResetPasswordDto {

    @NotNull
    @Size(min=6, max=6)
    private String code;

    @NotNull
    @NotEmpty
    @Size(min = 4, max=50)
    private String password;



    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }


}
