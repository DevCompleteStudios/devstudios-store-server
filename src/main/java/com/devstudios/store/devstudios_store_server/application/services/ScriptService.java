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
import com.devstudios.store.devstudios_store_server.application.interfaces.projections.IScriptProjection;
import com.devstudios.store.devstudios_store_server.application.interfaces.repositories.IScriptRepository;
import com.devstudios.store.devstudios_store_server.domain.entities.ScriptEntity;
import com.devstudios.store.devstudios_store_server.infrastructure.CustomExceptions.CustomException;




@Service
public class ScriptService {

    private final String pathImages = "/images/services";

    IScriptRepository scriptRepository;
    IFilesService filesService;

    public ScriptService( IScriptRepository scriptRepository, IFilesService filesService ){
        this.scriptRepository = scriptRepository;
        this.filesService = filesService;
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


    public ResponsePaginationDto<List<IScriptProjection>> findAll(PaginationDto paginationDto){
        Pageable page = PageRequest.of(paginationDto.getPage(), paginationDto.getElements());
        Page<IScriptProjection> scripts = scriptRepository.findAllScripts(page);

        List<IScriptProjection> elements = scripts.getContent();

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

}
