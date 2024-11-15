package com.devstudios.store.devstudios_store_server.application.services;

import java.time.LocalDateTime;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.devstudios.store.devstudios_store_server.application.dtos.auth.AuthDto;
import com.devstudios.store.devstudios_store_server.application.dtos.auth.ForgotPasswordDto;
import com.devstudios.store.devstudios_store_server.application.dtos.auth.ResetPasswordDto;
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

    private final String MESSAGE_CODE_ERROR = "code already expired";

    private final IUserRepository userRepository;
    private final IJwtService jwtService;
    private final IBcryptService bcrypt;
    private final AutoMapper mapper;
    private final IRandomService randomService;
    private final IMailerService mailerService;


    public AuthService( IUserRepository userRepository, IJwtService jwtService, IBcryptService bcrypt, AutoMapper mapper, IRandomService randomService,
        IMailerService mailerService){
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.bcrypt = bcrypt;
        this.mapper = mapper;
        this.randomService = randomService;
        this.mailerService = mailerService;
    }



    public ResponseDto<String> registerUser(AuthDto authDto){
        UserEntity user = new UserEntity();

        System.out.println("1");
        String token = jwtService.createJwt(user.getRoles(), authDto.getEmail());
        System.out.println("2");
        String passwordHash = bcrypt.hashPassword(authDto.getPassword());
        System.out.println("3");
        user.setEmail(authDto.getEmail());
        System.out.println("4");
        user.setPassword(passwordHash);
        System.out.println("5");

        userRepository.save(user);

        return new ResponseDto<>(token, 201, user.getEmail());
    }


    public ResponseDto<String> loginUser( AuthDto authDto ){
        UserEntity user = userRepository.findByEmail(authDto.getEmail())
            .orElseThrow( () -> CustomException.notFoundException("Account not exist"));

        if( !user.getIsActive() )
            throw CustomException.badRequestException("Account is not active");
        if( !bcrypt.comparePassword(authDto.getPassword(), user.getPassword()) )
            throw CustomException.badRequestException("Email or password is not valid");

        String token = jwtService.createJwt(user.getRoles(), authDto.getEmail());

        return new ResponseDto<>(token, 200, authDto.getEmail());
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


    public ResponseDto<IUserProjection> resetPassword(ResetPasswordDto resetPasswordDto){
        UserEntity user = userRepository.findByCodeAuth(resetPasswordDto.getCode())
            .orElseThrow( () -> CustomException.badRequestException(MESSAGE_CODE_ERROR));
        LocalDateTime now = LocalDateTime.now();


        if( user.getCodeAuth().getExpiredDate().isBefore(now) )
            throw CustomException.badRequestException(MESSAGE_CODE_ERROR);
        String passwordHash = bcrypt.hashPassword(resetPasswordDto.getPassword());

        user.setCodeAuth(null);
        user.setPassword(passwordHash);
        IUserProjection u = mapper.userEntityToProjection(user);
        String token = jwtService.createJwt(user.getRoles(), user.getEmail());

        userRepository.save(user);

        return new ResponseDto<>(token, 200, u);
    }


    public ResponseDto<IUserProjection> verifyToken(){
        String currentUser = SecurityContextHolder.getContext().getAuthentication().getName();
        UserEntity user = userRepository.findByEmail(currentUser)
            .orElseThrow( () -> CustomException.badRequestException("token expired"));

        String token = this.jwtService.createJwt(user.getRoles(), user.getEmail());
        IUserProjection userProjection = mapper.userEntityToProjection(user);

        return new ResponseDto<>(token, 200, userProjection);
    }

}
