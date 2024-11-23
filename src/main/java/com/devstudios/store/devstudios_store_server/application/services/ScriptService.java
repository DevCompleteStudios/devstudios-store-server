package com.devstudios.store.devstudios_store_server.application.services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.devstudios.store.devstudios_store_server.application.dtos.script.CreateScriptDto;
import com.devstudios.store.devstudios_store_server.application.dtos.script.FreeAccesScriptDto;
import com.devstudios.store.devstudios_store_server.application.dtos.script.UpdateScriptDto;
import com.devstudios.store.devstudios_store_server.application.dtos.shared.PaginationDto;
import com.devstudios.store.devstudios_store_server.application.dtos.shared.ResponseDto;
import com.devstudios.store.devstudios_store_server.application.dtos.shared.ResponsePaginationDto;
import com.devstudios.store.devstudios_store_server.application.interfaces.enums.TypePayment;
import com.devstudios.store.devstudios_store_server.application.interfaces.projections.IScriptPreviewProjection;
import com.devstudios.store.devstudios_store_server.application.interfaces.projections.IScriptProjection;
import com.devstudios.store.devstudios_store_server.application.interfaces.repositories.IScriptRepository;
import com.devstudios.store.devstudios_store_server.application.interfaces.repositories.IUserRepository;
import com.devstudios.store.devstudios_store_server.application.interfaces.services.IJwtService;
import com.devstudios.store.devstudios_store_server.application.interfaces.services.IMailerService;
import com.devstudios.store.devstudios_store_server.application.interfaces.services.IPaymentsService;
import com.devstudios.store.devstudios_store_server.domain.entities.ScriptEntity;
import com.devstudios.store.devstudios_store_server.domain.entities.UserEntity;
import com.devstudios.store.devstudios_store_server.infrastructure.CustomExceptions.CustomException;




@Service
public class ScriptService {

    @Value("${owner.email}")
    private String ownerEmail;

    private final String pathImages = "/images/services";

    IScriptRepository scriptRepository;
    IFilesService filesService;
    IPaymentsService paymentsService;
    IUserRepository userRepository;
    IJwtService jwtService;
    IMailerService mailerService;
    HandlePaymentsService handlePaymentsService;


    public ScriptService( IScriptRepository scriptRepository, IFilesService filesService, IPaymentsService paymentsService,
        IUserRepository userRepository, IJwtService jwtService, IMailerService mailerService, HandlePaymentsService handlePaymentsService ){
        this.scriptRepository = scriptRepository;
        this.filesService = filesService;
        this.paymentsService = paymentsService;
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.mailerService = mailerService;
        this.handlePaymentsService = handlePaymentsService;
    }


    public ResponseDto<ScriptEntity> create( CreateScriptDto dto ){
        ScriptEntity script = new ScriptEntity();
        String url = filesService.saveFile(pathImages, dto.getImage());

        script.setDescription(dto.getDescription());
        script.setMethodPayment(dto.getMethodPayment());
        script.setName(dto.getName());
        script.setPrice(dto.getPrice());
        script.setYoutubeLink(dto.getYoutubeLink());
        script.setImage(url);

        script = scriptRepository.save(script);

        return new ResponseDto<>(null, 201, script);
    }


    public ResponsePaginationDto<List<IScriptPreviewProjection>> findAll(PaginationDto paginationDto){
        Pageable page = PageRequest.of(paginationDto.getPage(), paginationDto.getElements());
        Page<IScriptPreviewProjection> scripts = scriptRepository.findAllScripts(page);

        List<IScriptPreviewProjection> elements = scripts.getContent();

        return new ResponsePaginationDto<>(scripts.getTotalPages(), scripts.getTotalElements(), null, 200, elements);
    }


    public ResponseDto<IScriptProjection> updateScript(Long id, UpdateScriptDto updateScriptDto ){
        if(
            updateScriptDto.getDescription() != null
            || updateScriptDto.getMethodPayment() != null
            || updateScriptDto.getName() != null
            || updateScriptDto.getPrice() != null
            || updateScriptDto.getYoutubeLink() != null
            || updateScriptDto.getImage() != null
        ){
            ScriptEntity script = scriptRepository.findById(id)
                .orElseThrow( () -> CustomException.notFoundException("Script not exist"));

            if( updateScriptDto.getPrice() != null )
                script.setPrice(updateScriptDto.getPrice());
            if( updateScriptDto.getDescription() != null )
                script.setDescription(updateScriptDto.getDescription());
            if( updateScriptDto.getName() != null )
                script.setName(updateScriptDto.getName());
            if( updateScriptDto.getMethodPayment() != null )
                script.setMethodPayment(updateScriptDto.getMethodPayment());
            if( updateScriptDto.getYoutubeLink() != null )
                script.setYoutubeLink(updateScriptDto.getYoutubeLink());
            if( updateScriptDto.getScriptText() != null )
                script.setScriptText(updateScriptDto.getScriptText());
            if( updateScriptDto.getImage() != null ){
                String image = filesService.updateFile(script.getImage(), pathImages, updateScriptDto.getImage());
                script.setImage(image);
            }

            scriptRepository.save(script);
        }

        return new ResponseDto<>(
            null,
            200,
            scriptRepository.findOneById(id)
                .orElseThrow( () -> CustomException.notFoundException("Error updating script, try again later."))
        );
    }


    public ResponseDto<IScriptProjection> findById(Long id){
        IScriptProjection script = scriptRepository.findOneById(id)
            .orElseThrow( () -> CustomException.notFoundException("Script not found"));

        return new ResponseDto<>(null, 200, script);
    }


    public ResponseDto<String> buyScript( Long id, String email ){
        ScriptEntity script = scriptRepository.findById(id)
            .orElseThrow( () -> CustomException.notFoundException("Script not found"));

        UserEntity user = userRepository.findByEmail(email)
            .orElseThrow( () -> CustomException.notFoundException("Unexpected error, try again later"));

        String token = jwtService.createJwt(user.getRoles(), user.getEmail());
        String url = paymentsService.createOrder(script.getName(), email, script.getDescription(), script.getPrice(), 1L, script.getImage(), script.getId(), TypePayment.ONE_PAYMENT);

        return new ResponseDto<>(token, 200, url);
    }


    public ResponseDto<Boolean> freeAccesScript( Long scriptId, FreeAccesScriptDto dto ){
        String currentUser = SecurityContextHolder.getContext().getAuthentication().getName();

        String text = "El usuario: " + currentUser + " ha generado un script gratis a: " + dto.getEmail() + " fecha: " + LocalDateTime.now();
        handlePaymentsService.HandlePayment(dto.getEmail(), scriptId, TypePayment.ONE_PAYMENT.name(), "Order generated by server");
        mailerService.sendEmail(text, ownerEmail, "Alguien genero un pago gratis!");
        return new ResponseDto<>(null, 201, true);
    }
}
