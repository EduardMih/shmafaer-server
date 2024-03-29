package com.licenta.shmafaerserver.service;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {
    private final JavaMailSender javaMailSender;

    @Async
    public void sendMail(SimpleMailMessage email)
    {
        javaMailSender.send(email);
        System.out.println("Sending mail...");
        System.out.println(email.getText());
    }
}
