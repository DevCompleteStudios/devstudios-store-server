package com.devstudios.store.devstudios_store_server.application.interfaces.services;


public interface IMailerService {
    public void sendEmail( String html, String to, String subject );

}
