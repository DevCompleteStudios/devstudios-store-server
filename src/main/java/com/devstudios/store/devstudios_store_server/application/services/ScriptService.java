package com.devstudios.store.devstudios_store_server.application.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.devstudios.store.devstudios_store_server.application.dtos.script.CreateScriptDto;
import com.devstudios.store.devstudios_store_server.application.dtos.script.UpdateScriptDto;
import com.devstudios.store.devstudios_store_server.application.dtos.shared.PaginationDto;
import com.devstudios.store.devstudios_store_server.application.dtos.shared.ResponseDto;
import com.devstudios.store.devstudios_store_server.application.dtos.shared.ResponsePaginationDto;
import com.devstudios.store.devstudios_store_server.application.interfaces.projections.IScriptPreviewProjection;
import com.devstudios.store.devstudios_store_server.application.interfaces.projections.IScriptProjection;
import com.devstudios.store.devstudios_store_server.application.interfaces.repositories.IScriptRepository;
import com.devstudios.store.devstudios_store_server.application.interfaces.repositories.IUserRepository;
import com.devstudios.store.devstudios_store_server.application.interfaces.services.IJwtService;
import com.devstudios.store.devstudios_store_server.application.interfaces.services.IPaymentsService;
import com.devstudios.store.devstudios_store_server.domain.entities.ScriptEntity;
import com.devstudios.store.devstudios_store_server.domain.entities.UserEntity;
import com.devstudios.store.devstudios_store_server.infrastructure.CustomExceptions.CustomException;




@Service
public class ScriptService {

    private final String pathImages = "/images/services";

    IScriptRepository scriptRepository;
    IFilesService filesService;
    IPaymentsService paymentsService;
    IUserRepository userRepository;
    IJwtService jwtService;

    public ScriptService( IScriptRepository scriptRepository, IFilesService filesService, IPaymentsService paymentsService,
        IUserRepository userRepository, IJwtService jwtService ){
        this.scriptRepository = scriptRepository;
        this.filesService = filesService;
        this.paymentsService = paymentsService;
        this.userRepository = userRepository;
        this.jwtService = jwtService;
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

        // obtenemos la informacion del usuario que envio el ticket de compra
        UserEntity user = userRepository.findById(id)
            .orElseThrow( () -> CustomException.notFoundException("Unexpected error, try again later"));

        // creamos un nuevo token
        String token = jwtService.createJwt(user.getRoles(), user.getEmail());

        // creamos la orden de compra
        String url = paymentsService.createOrder(script.getName(), email, script.getDescription(), script.getPrice(), 1L, script.getImage());

        // mandamos la informacion
        return new ResponseDto<>(token, 200, url);
    }

}
