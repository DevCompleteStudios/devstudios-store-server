package com.devstudios.store.devstudios_store_server.infrastructure.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.devstudios.store.devstudios_store_server.application.interfaces.enums.TypePayment;
import com.devstudios.store.devstudios_store_server.application.interfaces.services.IPaymentsService;
import com.devstudios.store.devstudios_store_server.application.interfaces.services.IRandomService;
import com.devstudios.store.devstudios_store_server.application.services.HandlePaymentsService;
import com.devstudios.store.devstudios_store_server.infrastructure.CustomExceptions.CustomException;
import com.stripe.Stripe;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import com.stripe.model.Event;
import com.stripe.model.EventDataObjectDeserializer;
import com.stripe.model.StripeObject;
import com.stripe.model.checkout.Session;
import com.stripe.net.Webhook;
import com.stripe.param.ChargeListParams;
import com.stripe.param.ChargeUpdateParams;
import com.stripe.param.checkout.SessionCreateParams;

import jakarta.annotation.PostConstruct;



@Service
public class PaymentsServiceStripeImpl implements IPaymentsService {

    @Autowired
    HandlePaymentsService handlePaymentsService;
    @Autowired
    IRandomService randomService;

    @Value("${stripe.api.key}")
    private String stripeApiKey;

    @Value("${stripe.webhook}")
    private String stripeWebhook;

    @Value("${image.default.logo}")
    private String imageDefault;

    @Value("${url.client}")
    private String CLIENT_URL;


    @PostConstruct
    public void init() {
        Stripe.apiKey = stripeApiKey;
    }




    @Override
    public String createOrder(String name, String customerEmail, String description, Double price, Long quantity, Long productId, TypePayment typePayment) {
        return this.createOrder(name, customerEmail, description, price, quantity, imageDefault, productId, typePayment);
    }


    @Override
    public String createOrder(String name, String customerEmail, String description, Double price, Long quantity, String image, Long productId, TypePayment typePayment) {
        SessionCreateParams params = SessionCreateParams.builder()
            .setMode(SessionCreateParams.Mode.PAYMENT)
            .setSuccessUrl(CLIENT_URL + "/payment-succes")
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
            .putMetadata("productId", productId.toString())
            .putMetadata("type", typePayment.name())
            .putMetadata("email", customerEmail)
            .build();

        try {
            Session session = Session.create(params);
            return session.getUrl();  // Esto obtiene la URL de la sesión de pago
        } catch (StripeException e) {
            throw CustomException.internalServerException(e.getMessage());
        }
    }


    @Override
    public void paymentsHook(String payload, String sigHeader) {
        Event event;
        try {
            event = Webhook.constructEvent(
                payload, sigHeader, stripeWebhook
            );
        } catch (SignatureVerificationException e) {
            throw CustomException.internalServerException(e.getMessage());
        }

        EventDataObjectDeserializer dataObjectDeserializer = event.getDataObjectDeserializer();

        StripeObject stripeObject = null;
        if (dataObjectDeserializer.getObject().isPresent()) {
            stripeObject = dataObjectDeserializer.getObject().get();
        } else {
            System.out.println(event.getApiVersion());
            System.out.println(Stripe.API_VERSION);
            throw CustomException.badRequestException("is not valid");
        }

        switch (event.getType()) {
            case "checkout.session.completed" ->  {
                Session paymentIntent = (Session) stripeObject;

                String email = paymentIntent.getMetadata().get("email");
                Long productId = Long.valueOf(paymentIntent.getMetadata().get("productId"));
                String type = paymentIntent.getMetadata().get("type");
                String orderId = randomService.uuid().toString();

                if( email != null && type != null && orderId != null ){
                    try {
                        ChargeListParams params = ChargeListParams.builder()
                        .setPaymentIntent(paymentIntent.getPaymentIntent())
                        .build();
                        List<Charge> charges = Charge.list(params).getData();

                        for (Charge charge : charges) {
                            ChargeUpdateParams updateParams = ChargeUpdateParams.builder()
                                .putMetadata("email", email)
                                .putMetadata("orderId", orderId)
                                .putMetadata("productId", productId.toString())
                                .putMetadata("type", type)
                                .build();
                            charge.update(updateParams);
                        }
                    } catch (StripeException e) {
                        throw CustomException.internalServerException("Error al manejar el pago de: " + email + " >> " + e.getMessage());
                    }

                    handlePaymentsService.HandlePayment(email, productId, type, orderId);
                }
            }
            case "charge.refunded" ->  {
                Charge charge = (Charge) stripeObject;
                String orderId = charge.getMetadata().get("orderId");
                String typePaymentMethod = charge.getMetadata().get("type");

                if( orderId != null && typePaymentMethod != null ){
                    handlePaymentsService.handleRefunded(orderId, typePaymentMethod);
                }
            }
            default -> System.out.println("Otro pago: " + event.getType());
        }
    }



}
