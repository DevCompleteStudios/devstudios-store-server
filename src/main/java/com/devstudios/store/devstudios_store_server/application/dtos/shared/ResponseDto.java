package com.devstudios.store.devstudios_store_server.application.dtos.shared;

import java.time.LocalDateTime;



public class ResponseDto<T> {

    private String message = "Succes";
    private String token;
    private int status;
    private LocalDateTime date = LocalDateTime.now();
    private T data;


    public ResponseDto(){}


    public ResponseDto(String token, int status, T data) {
        this.token = token;
        this.status = status;
        this.data = data;
    }

    public ResponseDto(T data, int status, String token, String message ) {
        this(token, status, data);
        this.message = message;
    }





    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public String getToken() {
        return token;
    }
    public void setToken(String token) {
        this.token = token;
    }
    public int getStatus() {
        return status;
    }
    public void setStatus(int status) {
        this.status = status;
    }
    public LocalDateTime getDate() {
        return date;
    }
    public void setDate(LocalDateTime date) {
        this.date = date;
    }
    public T getData() {
        return data;
    }
    public void setData(T data) {
        this.data = data;
    }

}
