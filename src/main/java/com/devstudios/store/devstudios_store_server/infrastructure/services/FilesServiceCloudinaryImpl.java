package com.devstudios.store.devstudios_store_server.infrastructure.services;


import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.devstudios.store.devstudios_store_server.application.services.IFilesService;
import com.devstudios.store.devstudios_store_server.infrastructure.CustomExceptions.CustomException;


@Service
public class FilesServiceCloudinaryImpl implements IFilesService {

    @Autowired
    private Cloudinary cloudinary;


    @Override
    public String saveFile(String path, MultipartFile file) {
        try {
            // Configuración de los parámetros de subida
            Map<String, Object> params = new HashMap<>();
            params.put("folder", path);
            params.put("use_filename", true);
            params.put("unique_filename", true);
            params.put("overwrite", true);

            // Subida del archivo
            Map<String, Object> uploadResult = cloudinary.uploader().upload(file.getBytes(), params);

            // Obtener y retornar la URL de la imagen subida
            return uploadResult.get("url").toString();
        } catch (IOException e) {
            throw CustomException.internalServerException(e.getMessage());
        }
    }

    @Override
    public Boolean deleteFile(String path) {
        String publicId = extractPublicIdFromUrl(path);

        try {
            Map<String, Object> deleteResult = cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
            System.out.println(deleteResult);
            System.out.println(publicId);
            return "ok".equals(deleteResult.get("result"));
        } catch (IOException e) {
            throw CustomException.internalServerException(e.getMessage());
        }
    }

    private String extractPublicIdFromUrl(String url) {
        String[] parts = url.split("/upload/");
        String pathWithoutVersion = parts[1].substring(parts[1].indexOf("/") + 1);

        String publicIdWithExtension = pathWithoutVersion.split("\\.")[0];
        return publicIdWithExtension;
    }

}
