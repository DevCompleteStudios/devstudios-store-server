package com.devstudios.store.devstudios_store_server.application.services;

import org.springframework.stereotype.Service;

import com.devstudios.store.devstudios_store_server.application.dtos.auth.AuthDto;
import com.devstudios.store.devstudios_store_server.application.dtos.shared.ResponseDto;
import com.devstudios.store.devstudios_store_server.application.interfaces.repositories.IUserRepository;
import com.devstudios.store.devstudios_store_server.domain.entities.UserEntity;




@Service
public class AuthService {

    IUserRepository userRepository;

    public AuthService( IUserRepository userRepository ){
        this.userRepository = userRepository;
    }



    public ResponseDto<UserEntity> registerUser(AuthDto authDto){
        UserEntity user = new UserEntity();

        user.setEmail(authDto.getEmail());

        //hashear su password cuando agreguemos spring security
        user.setPassword(authDto.getPassword());

        ResponseDto<UserEntity> res = new ResponseDto<>();

        res.setStatus(201);
        res.setData(userRepository.save(user));
        res.setMessage("Succes");
        res.setToken("token here");

        return res;
    }

    public ResponseDto<?> loginUser(){
        return null;
    }

    public ResponseDto<?> forgotPassword(){
        return null;
    }

    public ResponseDto<?> resetPassword(){
        return null;
    }

    public ResponseDto<?> verifyToken(){
        return null;
    }

}
