package com.devstudios.store.devstudios_store_server.presentation.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.devstudios.store.devstudios_store_server.application.interfaces.services.IPaymentsService;



@RestController
public class StripeController {

    @Autowired
    IPaymentsService paymentsService;


    @PostMapping("/webhook")
    public ResponseEntity<?> handleWebhook( @RequestBody String payload, @RequestHeader("Stripe-Signature") String sigHeader ){
        paymentsService.paymentsHook(payload, sigHeader);
        return ResponseEntity.ok().body("Succes");
    }

}
