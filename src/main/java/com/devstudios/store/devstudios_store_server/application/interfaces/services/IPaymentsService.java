package com.devstudios.store.devstudios_store_server.application.interfaces.services;

import com.devstudios.store.devstudios_store_server.application.interfaces.enums.TypePayment;

public interface IPaymentsService {

    public String createOrder(String name, String customerEmail, String description, Double price, Long quantity, String image, Long productId, TypePayment typePayment);
    public String createOrder(String name, String customerEmail, String description, Double price, Long quantity, Long productId, TypePayment typePayment);
    public void paymentsHook(String payload, String sigHeader);

}

