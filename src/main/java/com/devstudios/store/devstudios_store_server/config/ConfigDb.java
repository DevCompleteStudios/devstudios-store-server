package com.devstudios.store.devstudios_store_server.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableAsync;


@Configuration
@EnableAsync
@PropertySource("classpath:db.properties")
public class ConfigDb {}
