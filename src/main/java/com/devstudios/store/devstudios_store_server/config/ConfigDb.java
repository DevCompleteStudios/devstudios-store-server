package com.devstudios.store.devstudios_store_server.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
// @PropertySource("classpath:db-prod.properties")
@PropertySource("classpath:db-dev.properties")
public class ConfigDb {}