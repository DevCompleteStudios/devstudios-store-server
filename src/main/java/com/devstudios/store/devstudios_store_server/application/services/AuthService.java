package com.devstudios.store.devstudios_store_server.application.services;

import org.springframework.stereotype.Service;

import com.devstudios.store.devstudios_store_server.application.dtos.auth.AuthDto;
import com.devstudios.store.devstudios_store_server.application.dtos.shared.ResponseDto;
import com.devstudios.store.devstudios_store_server.application.interfaces.repositories.IUserRepository;
import com.devstudios.store.devstudios_store_server.application.interfaces.services.IJwtService;
import com.devstudios.store.devstudios_store_server.domain.entities.UserEntity;




@Service
public class AuthService {

    IUserRepository userRepository;
    IJwtService jwtService;


    public AuthService( IUserRepository userRepository, IJwtService jwtService ){
        this.userRepository = userRepository;
        this.jwtService = jwtService;
    }



    public ResponseDto<UserEntity> registerUser(AuthDto authDto){
        UserEntity user = new UserEntity();
        String token = jwtService.createJwt(user.getRoles(), authDto.getEmail());

        user.setEmail(authDto.getEmail());
        user.setPassword(authDto.getPassword());

        ResponseDto<UserEntity> res = new ResponseDto<>();

        res.setStatus(201);
        res.setData(userRepository.save(user));
        res.setMessage("Succes");
        res.setToken(token);

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
