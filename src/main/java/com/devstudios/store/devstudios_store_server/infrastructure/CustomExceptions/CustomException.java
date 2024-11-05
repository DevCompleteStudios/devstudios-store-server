package com.devstudios.store.devstudios_store_server.infrastructure.CustomExceptions;




public class CustomException extends RuntimeException {

    private String error;
    private int status;


    protected CustomException(String error, int status){
        super(error);
        this.error = error;
        this.status = status;
    }


    public static CustomException badRequestException(String error){
        return new CustomException(error, 400);
    }

    public static CustomException notFoundException(String error){
        return new CustomException(error, 404);
    }

    public static CustomException internalServerException(String error){
        System.out.println(error);
        return new CustomException("Unexpected error, please try again later", 404);
    }


    public String getError() {
        return error;
    }
    public void setError(String error) {
        this.error = error;
    }
    public int getStatus() {
        return status;
    }
    public void setStatus(int status) {
        this.status = status;
    }

}
