package com.devstudios.store.devstudios_store_server.application.interfaces.services;





public interface IPaymentsService {

    public String createOrder(String name, String customerEmail, String description, Double price, Long quantity, String image);

}

