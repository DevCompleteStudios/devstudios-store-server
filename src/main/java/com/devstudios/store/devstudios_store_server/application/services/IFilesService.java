package com.devstudios.store.devstudios_store_server.application.services;

import org.springframework.web.multipart.MultipartFile;



public interface IFilesService {

    public String saveFile( String path, MultipartFile file );
    public String deleteFile( String path );
    public default String updateFile( String urlImage, String path, MultipartFile file ){
        deleteFile(urlImage);
        return saveFile(path, file);
    }

}

