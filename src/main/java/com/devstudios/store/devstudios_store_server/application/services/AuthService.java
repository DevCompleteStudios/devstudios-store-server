package com.devstudios.store.devstudios_store_server.application.services;

import java.time.LocalDateTime;
import java.util.Objects;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.devstudios.store.devstudios_store_server.application.dtos.auth.AuthDto;
import com.devstudios.store.devstudios_store_server.application.dtos.auth.ForgotPasswordDto;
import com.devstudios.store.devstudios_store_server.application.dtos.auth.ResetPasswordDto;
import com.devstudios.store.devstudios_store_server.application.dtos.auth.VerifyAccesScriptDto;
import com.devstudios.store.devstudios_store_server.application.dtos.shared.ResponseDto;
import com.devstudios.store.devstudios_store_server.application.interfaces.enums.ScriptMethodPayment;
import com.devstudios.store.devstudios_store_server.application.interfaces.projections.IUserProjection;
import com.devstudios.store.devstudios_store_server.application.interfaces.repositories.IKeyRepository;
import com.devstudios.store.devstudios_store_server.application.interfaces.repositories.IScriptRepository;
import com.devstudios.store.devstudios_store_server.application.interfaces.repositories.IUserRepository;
import com.devstudios.store.devstudios_store_server.application.interfaces.services.IBcryptService;
import com.devstudios.store.devstudios_store_server.application.interfaces.services.IJwtService;
import com.devstudios.store.devstudios_store_server.application.interfaces.services.IMailerService;
import com.devstudios.store.devstudios_store_server.application.interfaces.services.IRandomService;
import com.devstudios.store.devstudios_store_server.domain.entities.CodeAuthEntity;
import com.devstudios.store.devstudios_store_server.domain.entities.KeyEntity;
import com.devstudios.store.devstudios_store_server.domain.entities.ScriptEntity;
import com.devstudios.store.devstudios_store_server.domain.entities.ScriptPurchaseEntity;
import com.devstudios.store.devstudios_store_server.domain.entities.SubscriptionPurchaseEntity;
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
    private final IKeyRepository keyRepository;
    private final IScriptRepository scriptRepository;


    public AuthService( IUserRepository userRepository, IJwtService jwtService, IBcryptService bcrypt, AutoMapper mapper, IRandomService randomService,
        IMailerService mailerService, IKeyRepository keyRepository, IScriptRepository scriptRepository){
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.bcrypt = bcrypt;
        this.mapper = mapper;
        this.randomService = randomService;
        this.mailerService = mailerService;
        this.keyRepository = keyRepository;
        this.scriptRepository = scriptRepository;
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




    public ResponseDto<Boolean> verifyAccesScript( Long scriptId, VerifyAccesScriptDto dto ){
        KeyEntity key;

        if( dto.getKey() == null ){
            key = keyRepository.findByRobloxIdAndScriptId(dto.getRobloxId(), scriptId)
                .orElseThrow( () -> CustomException.notFoundException("Enter key"));
        } else {
            key = keyRepository.findByValue(dto.getKey())
                .orElseThrow( () -> CustomException.badRequestException("Key is not valid!"));
        }

        if( !key.getIsActive() ) throw CustomException.badRequestException("Key is not valid!");
        if( key.getCurrentUserRobloxId() != null && !key.getCurrentUserRobloxId().equals(dto.getRobloxId()) )
            throw CustomException.badRequestException("This user is not valid for this key");

        ScriptEntity currentScript = scriptRepository.findById(scriptId)
            .orElseThrow( () -> CustomException.notFoundException("Script not found"));
        if( !currentScript.getIsActive() ) throw CustomException.notFoundException("Script not found");

        if(key.getScriptPurchase() != null){
            this.validateScriptPurchase(currentScript, key.getScriptPurchase());
        } else if(key.getSubscriptionPurchase() != null) {
            this.validateSubscriptionPurhcase(currentScript, key.getSubscriptionPurchase());
        } else {
            throw CustomException.badRequestException("Script not found");
        }

        if( key.getCurrentUserRobloxId() == null ){
            key.setCurrentUserRobloxId(dto.getRobloxId());
            keyRepository.save(key);
        }
        return new ResponseDto<>(null, 200, true);
    }

    private Boolean validateScriptPurchase(ScriptEntity currentScript, ScriptPurchaseEntity scriptPurchase){
        if( !scriptPurchase.getIsActive() ) throw CustomException.notFoundException("Purchase is not valid, contact support");
        if( !Objects.equals(currentScript.getId(), scriptPurchase.getScript().getId()))
            throw CustomException.badRequestException("This purchase is not valid in this script!");

        return true;
    }
    private Boolean validateSubscriptionPurhcase(ScriptEntity currentScript, SubscriptionPurchaseEntity subscriptionPurchaseEntity){
        if( currentScript.getMethodPayment().equals(ScriptMethodPayment.ONE_PAYMENT) )
            throw CustomException.badRequestException("This script is not available in subscription");

        LocalDateTime currentDate = LocalDateTime.now();
        if( subscriptionPurchaseEntity.getDateExpired().isBefore(currentDate) )
            throw CustomException.badRequestException("Key expired!");
        if( !subscriptionPurchaseEntity.getIsActive() )
            throw CustomException.badRequestException("Key expired!");
        
        return true;
    }

}
