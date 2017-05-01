package com.chekhovych.service.impl;

import com.chekhovych.service.GmailService;
import it.ozimov.springboot.mail.model.Email;
import it.ozimov.springboot.mail.model.defaultimpl.DefaultEmail;
import it.ozimov.springboot.mail.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.internet.InternetAddress;
import java.io.UnsupportedEncodingException;

import static com.google.common.collect.Lists.newArrayList;

@Service
public class GmailServiceImpl implements GmailService{

    @Autowired
    private EmailService emailService;

    @Override
    public void sendEmail(String message) throws UnsupportedEncodingException {
        final Email email = DefaultEmail.builder()
                .from(new InternetAddress("zorozoro30000@gmail.com",
                        "LNU Applied Mathematics faculty"))
                .to(newArrayList(
                        new InternetAddress("chekhovych1995@gmail.com",
                                "Andriy Chekhovych")))
                .subject(message)
                .body("Post was successfully deleted !!!")
                .encoding("UTF-8").build();

        emailService.send(email);
    }
}
