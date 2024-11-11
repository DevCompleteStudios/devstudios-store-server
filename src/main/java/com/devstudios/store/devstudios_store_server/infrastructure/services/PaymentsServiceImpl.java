package com.devstudios.store.devstudios_store_server.infrastructure.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.devstudios.store.devstudios_store_server.application.interfaces.services.IPaymentsService;
import com.devstudios.store.devstudios_store_server.infrastructure.CustomExceptions.CustomException;
import com.stripe.Stripe;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;

import jakarta.annotation.PostConstruct;



@Service
public class PaymentsServiceImpl implements IPaymentsService {

    @Value("${stripe.api.key}")
    private String stripeApiKey;

    private final String CLIENT_URL = "http://localhost:4242/home";


    @PostConstruct
    public void init() {
        Stripe.apiKey = stripeApiKey;
    }


    @Override
    public String createOrder(String name, String customerEmail, String description, Double price, Long quantity, String image) {
        SessionCreateParams params = SessionCreateParams.builder()
            .setMode(SessionCreateParams.Mode.PAYMENT)
            .setSuccessUrl(CLIENT_URL)
            .setCancelUrl(CLIENT_URL)
            .setCustomerEmail(customerEmail)
            .addLineItem(
                SessionCreateParams.LineItem.builder()
                    .setQuantity(quantity)
                    .setPriceData(
                        SessionCreateParams.LineItem.PriceData.builder()
                            .setCurrency("usd")
                            .setUnitAmount((long) (price * 100))
                            .setProductData(
                                SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                    .setName(name)
                                    .setDescription(description)
                                    .addImage(image)
                                    .build()
                            )
                            .build()
                    )
                    .build()
            )
            .build();

        try {
            Session session = Session.create(params);
            return session.getUrl();  // Esto obtiene la URL de la sesión de pago
        } catch (Exception e) {
            throw CustomException.internalServerException(e.getMessage());
        }
    }


}
