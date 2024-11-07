package com.devstudios.store.devstudios_store_server.infrastructure.services;


import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.devstudios.store.devstudios_store_server.application.services.IFilesService;


@Service
public class FilesServiceImpl implements IFilesService {

    @Override
    public String saveFile(String path, MultipartFile file) {
        throw new UnsupportedOperationException("Unimplemented method 'saveFile'");
    }

    @Override
    public String deleteFile(String path) {
        throw new UnsupportedOperationException("Unimplemented method 'deleteFile'");
    }

}
