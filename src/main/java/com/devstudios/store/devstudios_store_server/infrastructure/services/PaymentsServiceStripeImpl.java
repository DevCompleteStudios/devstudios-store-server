package com.devstudios.store.devstudios_store_server.infrastructure.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.devstudios.store.devstudios_store_server.application.interfaces.enums.TypePayment;
import com.devstudios.store.devstudios_store_server.application.interfaces.services.IPaymentsService;
import com.devstudios.store.devstudios_store_server.infrastructure.CustomExceptions.CustomException;
import com.stripe.Stripe;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.model.Event;
import com.stripe.model.checkout.Session;
import com.stripe.net.Webhook;
import com.stripe.param.checkout.SessionCreateParams;

import jakarta.annotation.PostConstruct;



@Service
public class PaymentsServiceStripeImpl implements IPaymentsService {

    @Value("${stripe.api.key}")
    private String stripeApiKey;

    @Value("${stripe.webhook}")
    private String stripeWebhook;

    private final String CLIENT_URL = "http://localhost:4200/home";


    @PostConstruct
    public void init() {
        Stripe.apiKey = stripeApiKey;
    }


    @Override
    public String createOrder(String name, String customerEmail, String description, Double price, Long quantity, String image, String orderId, TypePayment typePayment) {
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
            .putMetadata("orderId", orderId)
            .putMetadata("type", typePayment.name())
            .putMetadata("email", customerEmail)
            .build();

        try {
            Session session = Session.create(params);
            return session.getUrl();  // Esto obtiene la URL de la sesión de pago
        } catch (Exception e) {
            throw CustomException.internalServerException(e.getMessage());
        }
    }


    @Override
    public void paymentsHook(String payload, String sigHeader) {
        try {
            Event event = Webhook.constructEvent(payload, sigHeader, stripeWebhook);

            if ("checkout.session.completed".equals(event.getType())) {
                Session session = (Session) event.getDataObjectDeserializer().getObject().orElseThrow();

                // obtenemos la metadata
                String email = session.getMetadata().get("email");
                String orderId = session.getMetadata().get("orderId");
                String type = session.getMetadata().get("type");

                if( email != null && orderId != null && type != null ){
                    System.out.println("Alguien ha pagado: " + email + " - " + orderId + " - " + type);
                }
            }
        } catch (SignatureVerificationException e) {
            throw CustomException.internalServerException(e.getMessage());
        }
    }




}
