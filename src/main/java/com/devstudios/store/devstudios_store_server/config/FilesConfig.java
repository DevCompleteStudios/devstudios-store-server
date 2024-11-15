package com.devstudios.store.devstudios_store_server.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cloudinary.Cloudinary;



@Configuration
public class FilesConfig {

    @Value("${cloudinary.url}")
    private String url;

    @Bean
    Cloudinary cloudinary() {
        Cloudinary cloudinary = new Cloudinary(url);
        return cloudinary;
    }

}
