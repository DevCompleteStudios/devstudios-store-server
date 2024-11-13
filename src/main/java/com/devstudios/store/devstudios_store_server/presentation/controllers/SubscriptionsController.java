package com.devstudios.store.devstudios_store_server.presentation.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devstudios.store.devstudios_store_server.application.dtos.subscription.CreateSubscriptionDto;
import com.devstudios.store.devstudios_store_server.application.dtos.subscription.EditSubscriptionDto;
import com.devstudios.store.devstudios_store_server.application.services.SubscriptionsService;

import jakarta.validation.Valid;






@RestController
@RequestMapping("/api/subscriptions")
public class SubscriptionsController {

    @Autowired
    SubscriptionsService service;


    @GetMapping("/get-all")
    public ResponseEntity<?> getAll(){
        var res = service.findAll();
        return ResponseEntity.status(200).body(res);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/create")
    public ResponseEntity<?> createSubscription( @Valid @RequestBody CreateSubscriptionDto dto ) {
        var res = service.create(dto);
        return ResponseEntity.status(res.getStatus()).body(res);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("edit/{id}")
    public ResponseEntity<?> putMethodName(@PathVariable Long id, @Valid @RequestBody EditSubscriptionDto editSubscriptionDto ) {
        var res = service.edit(editSubscriptionDto, id);
        return ResponseEntity.status(res.getStatus()).body(res);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete( @PathVariable Long id ){
        var res = service.delete(id);
        return ResponseEntity.status(res.getStatus()).body(res);
    }

    @GetMapping("/buy/{id}")
    public ResponseEntity<?> buy( @PathVariable Long id ) {
        String currentUser = SecurityContextHolder.getContext().getAuthentication().getName();
        var res = service.buy(id, currentUser);
        return ResponseEntity.status(res.getStatus()).body(res);
    }


}
