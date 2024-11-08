package com.devstudios.store.devstudios_store_server.infrastructure.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.devstudios.store.devstudios_store_server.application.interfaces.services.IMailerService;
import com.devstudios.store.devstudios_store_server.infrastructure.CustomExceptions.CustomException;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;



@Async
@Service
public class MailerServiceImpl implements IMailerService {

    @Autowired
    JavaMailSender mailerSender;


    @Override
    public void sendEmail(String html, String to, String subject) {
        try {
            MimeMessage mimeMail = mailerSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMail, true, "UTF-8");

            helper.setTo(to);
            helper.setText(html, true);
            helper.setSubject(subject);

            mailerSender.send(mimeMail);
        } catch (MessagingException e) {
            throw CustomException.internalServerException(e.getMessage());
        }
    }


}
