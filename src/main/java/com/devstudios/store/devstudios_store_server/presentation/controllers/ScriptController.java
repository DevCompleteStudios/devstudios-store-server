package com.devstudios.store.devstudios_store_server.presentation.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devstudios.store.devstudios_store_server.application.dtos.script.CreateScriptDto;

import jakarta.validation.Valid;




@RestController
@RequestMapping("/api/scripts")
public class ScriptController {


    @PostMapping("/create-script-service")
    public ResponseEntity<?> postMethodName( @Valid @RequestBody CreateScriptDto scriptDto) {

        return null;
    }


}
