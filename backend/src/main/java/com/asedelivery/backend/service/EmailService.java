package com.asedelivery.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender emailSender;

    @Autowired
    private SpringTemplateEngine templateEngine;

    @Value("${spring.mail.from}")
    private String FROM;

    public void sendMail(String to, String subject, String text) throws MessagingException {
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, "utf-8");

        Context context = new Context();
        context.setVariable("name", "Developer!");
        context.setVariable("location", "United States");
        context.setVariable("sign", "Java Developer");
        String html = templateEngine.process("newsletter-template", context);

        helper.setFrom(FROM);
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(html, true);
        emailSender.send(message);
    }
}
