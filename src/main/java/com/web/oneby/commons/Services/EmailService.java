package com.web.oneby.commons.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class EmailService {
    private JavaMailSender sender;

    @Autowired
    public EmailService(
            JavaMailSender sender
    ){
        this.sender = sender;
    }

    public void send(String text, String email){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("OneBy");
        message.setText(text);
        sender.send(message);
    }
}
