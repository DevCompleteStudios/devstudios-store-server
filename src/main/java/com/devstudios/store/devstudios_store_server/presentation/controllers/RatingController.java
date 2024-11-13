package com.devstudios.store.devstudios_store_server.presentation.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devstudios.store.devstudios_store_server.application.dtos.rating.AddRatingDto;
import com.devstudios.store.devstudios_store_server.application.dtos.shared.PaginationDto;
import com.devstudios.store.devstudios_store_server.application.services.RatingService;

import jakarta.validation.Valid;



@RestController
@RequestMapping("/api/rating")
public class RatingController {

    @Autowired
    RatingService service;


    @PostMapping("/add-coment/{id}")
    public ResponseEntity<?> addComent( @PathVariable Long id, @Valid @RequestBody AddRatingDto addRatingDto ){
        var res = service.addComent(id, addRatingDto);
        return ResponseEntity.status(201).body(res);
    }

    @GetMapping("/get-coments-by-script-id/{id}")
    public ResponseEntity<?> getComentsById( @PathVariable Long id, @Valid @ModelAttribute PaginationDto paginationDto ){
        var res = service.getAllComentsByScriptId(id, paginationDto);
        return ResponseEntity.status(res.getStatus()).body(res);
    }

}
