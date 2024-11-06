package com.devstudios.store.devstudios_store_server.application.services;

import org.springframework.stereotype.Service;

import com.devstudios.store.devstudios_store_server.application.dtos.auth.AuthDto;
import com.devstudios.store.devstudios_store_server.application.dtos.shared.ResponseDto;
import com.devstudios.store.devstudios_store_server.application.interfaces.projections.IUserProjection;
import com.devstudios.store.devstudios_store_server.application.interfaces.repositories.IUserRepository;
import com.devstudios.store.devstudios_store_server.application.interfaces.services.IBcryptService;
import com.devstudios.store.devstudios_store_server.application.interfaces.services.IJwtService;
import com.devstudios.store.devstudios_store_server.domain.entities.UserEntity;
import com.devstudios.store.devstudios_store_server.domain.mappers.AutoMapper;




@Service
public class AuthService {

    IUserRepository userRepository;
    IJwtService jwtService;
    IBcryptService bcrypt;
    AutoMapper mapper;


    public AuthService( IUserRepository userRepository, IJwtService jwtService, IBcryptService bcrypt, AutoMapper mapper ){
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.bcrypt = bcrypt;
        this.mapper = mapper;
    }



    public ResponseDto<IUserProjection> registerUser(AuthDto authDto){
        UserEntity user = new UserEntity();
        String token = jwtService.createJwt(user.getRoles(), authDto.getEmail());
        String passwordHash = bcrypt.hashPassword(authDto.getPassword());

        user.setEmail(authDto.getEmail());
        user.setPassword(passwordHash);

        UserEntity userDb = userRepository.save(user);
        ResponseDto<IUserProjection> res = new ResponseDto<>();

        res.setStatus(201);
        res.setData(
            mapper.userEntityToProjection(userDb)
        );
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
