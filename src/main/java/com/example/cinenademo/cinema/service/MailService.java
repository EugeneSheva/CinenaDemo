package com.example.cinenademo.cinema.service;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;

@Service
public class MailService {

    @Autowired
    private JavaMailSender mailSendler;

    @Value("${spring.mail.username}")
    private String username;

    @SneakyThrows
    public void send(String[] emailTo, String subject, String message) {
        MimeMessage mailmessage = mailSendler.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mailmessage);
        helper.setFrom(username);
        helper.setTo(emailTo);
        helper.setSubject(subject);
        helper.setText(message, true);

        mailSendler.send(mailmessage);
    }

//    @SneakyThrows
//    public void send(String emailTo, String subject, String message) {
//        MimeMessage mailmessage = mailSendler.createMimeMessage();
//        MimeMessageHelper helper = new MimeMessageHelper(mailmessage);
//        helper.setFrom(username);
//        helper.setTo(emailTo);
//        helper.setSubject(subject);
//        helper.setText(message, false);
//
//        mailSendler.send(mailmessage);
//    }
}
