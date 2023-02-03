package com.asedelivery.backend.email;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

public class Email {

    private JavaMailSender emailSender;
    private SpringTemplateEngine templateEngine;

    String template;
    MimeMessage message;
    MimeMessageHelper helper;
    Context context;

    public Email(JavaMailSender emailSender, SpringTemplateEngine templateEngine, String template) throws MessagingException {
        this.emailSender = emailSender;
        this.templateEngine = templateEngine;
        this.template = template;
        message = emailSender.createMimeMessage();
        helper = new MimeMessageHelper(message, "utf-8");
        helper = new MimeMessageHelper(message, "utf-8");
        context = new Context();
    }

    public Email(JavaMailSender emailSender, SpringTemplateEngine templateEngine, String template, String from, String to, String subject) throws MessagingException {
        this(emailSender, templateEngine, template);

        setFrom(from);
        setTo(to);
        setSubject(subject);
    }

    protected void setFrom(String from) throws MessagingException {
        helper.setFrom(from);
    }

    protected void setTo(String to) throws MessagingException {
        helper.setTo(to);
    }

    protected void setSubject(String subject) throws MessagingException{
        helper.setSubject(subject);
    }

    protected void set(String name, Object obj){
        context.setVariable(name, obj);
    }

    public void send() throws MessagingException {
        String html = templateEngine.process(template, context);
        helper.setText(html, true);
        emailSender.send(message);
    }
}
