package com.devstudios.store.devstudios_store_server.presentation.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devstudios.store.devstudios_store_server.application.dtos.auth.AuthDto;
import com.devstudios.store.devstudios_store_server.application.dtos.auth.ForgotPasswordDto;
import com.devstudios.store.devstudios_store_server.application.dtos.auth.ResetPasswordDto;
import com.devstudios.store.devstudios_store_server.application.services.AuthService;

import jakarta.validation.Valid;



@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    AuthService service;

    @PostMapping("/register")
    public ResponseEntity<?> register( @Valid @RequestBody AuthDto authDto ){
        var res = service.registerUser(authDto);
        return ResponseEntity.status(res.getStatus()).body(res);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login( @Valid @RequestBody AuthDto authDto ){
        var res = service.loginUser(authDto);
        return ResponseEntity.status(res.getStatus()).body(res);
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword( @Valid @RequestBody ForgotPasswordDto forgotPasswordDto ){
        var res = service.forgotPassword(forgotPasswordDto);
        return ResponseEntity.status(res.getStatus()).body(res);
    }

    @PutMapping("/reset-password")
    public ResponseEntity<?> resetPassword( @Valid @RequestBody ResetPasswordDto resetPasswordDto ){
        var res = service.resetPassword(resetPasswordDto);
        return ResponseEntity.status(res.getStatus()).body(res);
    }

    @GetMapping("/verify-token")
    public ResponseEntity<?> validateToken(){
        var res = service.verifyToken();
        return ResponseEntity.status(res.getStatus()).body(res);
    }


}
