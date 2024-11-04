package com.devstudios.store.devstudios_store_server.presentation.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devstudios.store.devstudios_store_server.application.services.AuthService;



@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    AuthService service;

    @PostMapping("/register")
    public ResponseEntity<?> register(){
        var res = service.registerUser();
        return ResponseEntity.status(res.getStatus()).body(res);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(){
        var res = service.loginUser();
        return ResponseEntity.status(res.getStatus()).body(res);
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(){
        var res = service.forgotPassword();
        return ResponseEntity.status(res.getStatus()).body(res);
    }

    @PutMapping("/reset-password")
    public ResponseEntity<?> resetPassword(){
        var res = service.resetPassword();
        return ResponseEntity.status(res.getStatus()).body(res);
    }

    @GetMapping("/verify-token")
    public ResponseEntity<?> validateToken(){
        var res = service.verifyToken();
        return ResponseEntity.status(res.getStatus()).body(res);
    }


}
