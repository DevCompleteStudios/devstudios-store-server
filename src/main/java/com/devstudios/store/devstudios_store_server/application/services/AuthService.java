package com.devstudios.store.devstudios_store_server.application.services;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.devstudios.store.devstudios_store_server.application.dtos.auth.AuthDto;
import com.devstudios.store.devstudios_store_server.application.dtos.auth.ForgotPasswordDto;
import com.devstudios.store.devstudios_store_server.application.dtos.shared.ResponseDto;
import com.devstudios.store.devstudios_store_server.application.interfaces.projections.IUserProjection;
import com.devstudios.store.devstudios_store_server.application.interfaces.repositories.IUserRepository;
import com.devstudios.store.devstudios_store_server.application.interfaces.services.IBcryptService;
import com.devstudios.store.devstudios_store_server.application.interfaces.services.IJwtService;
import com.devstudios.store.devstudios_store_server.application.interfaces.services.IMailerService;
import com.devstudios.store.devstudios_store_server.application.interfaces.services.IRandomService;
import com.devstudios.store.devstudios_store_server.domain.entities.CodeAuthEntity;
import com.devstudios.store.devstudios_store_server.domain.entities.UserEntity;
import com.devstudios.store.devstudios_store_server.domain.mappers.AutoMapper;
import com.devstudios.store.devstudios_store_server.infrastructure.CustomExceptions.CustomException;




@Service
public class AuthService {

    IUserRepository userRepository;
    IJwtService jwtService;
    IBcryptService bcrypt;
    AutoMapper mapper;
    IRandomService randomService;
    IMailerService mailerService;


    public AuthService( IUserRepository userRepository, IJwtService jwtService, IBcryptService bcrypt, AutoMapper mapper, IRandomService randomService,
        IMailerService mailerService){
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.bcrypt = bcrypt;
        this.mapper = mapper;
        this.randomService = randomService;
        this.mailerService = mailerService;
    }



    public ResponseDto<IUserProjection> registerUser(AuthDto authDto){
        UserEntity user = new UserEntity();

        String token = jwtService.createJwt(user.getRoles(), authDto.getEmail());
        String passwordHash = bcrypt.hashPassword(authDto.getPassword());

        user.setEmail(authDto.getEmail());
        user.setPassword(passwordHash);

        UserEntity userDb = userRepository.save(user);
        IUserProjection userProjection = mapper.userEntityToProjection(userDb);

        return new ResponseDto<>(token, 201, userProjection);
    }


    public ResponseDto<IUserProjection> loginUser( AuthDto authDto ){
        UserEntity user = userRepository.findByEmail(authDto.getEmail())
            .orElseThrow( () -> CustomException.notFoundException("Account not exist"));

        if( !user.getIsActive() )
            throw CustomException.badRequestException("Account is not active");
        if( !bcrypt.comparePassword(authDto.getPassword(), user.getPassword()) )
            throw CustomException.badRequestException("Email or password is not valid");

        IUserProjection userProjection = mapper.userEntityToProjection(user);
        String token = jwtService.createJwt(user.getRoles(), authDto.getEmail());

        return new ResponseDto<>(token, 200, userProjection);
    }


    public ResponseDto<String> forgotPassword( ForgotPasswordDto forgotPasswordDto ){
        UserEntity user = userRepository.findByEmail(forgotPasswordDto.getEmail())
            .orElseThrow( () -> CustomException.notFoundException("Account not exists"));

        String code = randomService.randomsDigits(6);
        CodeAuthEntity codeAuth = new CodeAuthEntity(code);

        user.setCodeAuth(codeAuth);
        userRepository.save(user);
        mailerService.sendEmail(code, user.getEmail(), "Code autentication");

        return new ResponseDto<>(null, 200, "Code autentication send");
    }


    public ResponseDto<?> resetPassword(){
        return null;
    }


    public ResponseDto<?> verifyToken(){
        return new ResponseDto<>("a", 200, SecurityContextHolder.getContext().getAuthentication().getName());
    }

}
