package com.devstudios.store.devstudios_store_server.application.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.devstudios.store.devstudios_store_server.application.dtos.script.CreateScriptDto;
import com.devstudios.store.devstudios_store_server.application.dtos.shared.PaginationDto;
import com.devstudios.store.devstudios_store_server.application.dtos.shared.ResponseDto;
import com.devstudios.store.devstudios_store_server.application.dtos.shared.ResponsePaginationDto;
import com.devstudios.store.devstudios_store_server.application.interfaces.projections.IScriptProjection;
import com.devstudios.store.devstudios_store_server.application.interfaces.repositories.IScriptRepository;
import com.devstudios.store.devstudios_store_server.domain.entities.ScriptEntity;




@Service
public class ScriptService {

    IScriptRepository scriptRepository;

    public ScriptService( IScriptRepository scriptRepository ){
        this.scriptRepository = scriptRepository;
    }


    public ResponseDto<ScriptEntity> create( CreateScriptDto dto ){
        ScriptEntity script = new ScriptEntity();

        script.setDescription(dto.getDescription());
        script.setMethodPayment(dto.getMethodPayment());
        script.setName(dto.getName());
        script.setPrice(dto.getPrice());

        script = scriptRepository.save(script);

        return new ResponseDto<>(null, 201, script);
    }

    public ResponsePaginationDto<List<IScriptProjection>> findAll(PaginationDto paginationDto){
        Pageable page = PageRequest.of(paginationDto.getPage(), paginationDto.getElements());
        Page<IScriptProjection> scripts = scriptRepository.findAllScripts(page);

        List<IScriptProjection> elements = scripts.getContent();

        return new ResponsePaginationDto<>(scripts.getTotalPages(), scripts.getTotalElements(), null, 200, elements);
    }

}
