package com.devstudios.store.devstudios_store_server.presentation.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devstudios.store.devstudios_store_server.application.dtos.rating.AddRatingDto;
import com.devstudios.store.devstudios_store_server.application.dtos.script.CreateScriptDto;
import com.devstudios.store.devstudios_store_server.application.dtos.script.UpdateScriptDto;
import com.devstudios.store.devstudios_store_server.application.dtos.shared.PaginationDto;
import com.devstudios.store.devstudios_store_server.application.services.ScriptService;

import jakarta.validation.Valid;






@RestController
@RequestMapping("/api/scripts")
public class ScriptController {

    @Autowired
    ScriptService service;


    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/create-script")
    public ResponseEntity<?> postMethodName( @Valid @ModelAttribute CreateScriptDto scriptDto) {
        var res = service.create(scriptDto);
        return ResponseEntity.status(res.getStatus()).body(res);
    }

    @GetMapping("/find-all")
    public ResponseEntity<?> findAll( @Valid @ModelAttribute PaginationDto paginationDto) {
        var res = service.findAll(paginationDto);
        return ResponseEntity.status(200).body(res);
    }

    @GetMapping("/find-by-id/{id}")
    public ResponseEntity<?> findById( @PathVariable Long id ) {
        var res = service.findById(id);
        return ResponseEntity.status(res.getStatus()).body(res);
    }


    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/update-script/{id}")
    public ResponseEntity<?> updateScript( @PathVariable Long id, @Valid @ModelAttribute UpdateScriptDto updateScriptDto ){
        var res = service.updateScript(id, updateScriptDto);
        return ResponseEntity.status(res.getStatus()).body(res);
    }

    @GetMapping("/buy-script/{id}")
    public ResponseEntity<?> buyScript( @PathVariable Long id ) {
        String currentUser = SecurityContextHolder.getContext().getAuthentication().getName();
        var res = service.buyScript(id, currentUser);
        return ResponseEntity.status(res.getStatus()).body(res);
    }

    @GetMapping("/find-coments-by-script-id/{id}")
    public ResponseEntity<?> findComentsByScriptId( @PathVariable Long id, @Valid @ModelAttribute PaginationDto paginationDto ) {
        return null;
    }

    @PostMapping("/add-rating/{id}")
    public ResponseEntity<?> addRating( @PathVariable Long id, @Valid AddRatingDto addRatingDto ){
        return null;
    }


}
