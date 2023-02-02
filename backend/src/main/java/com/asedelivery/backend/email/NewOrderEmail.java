package com.asedelivery.backend.email;

import org.springframework.mail.javamail.JavaMailSender;
import org.thymeleaf.spring6.SpringTemplateEngine;

import com.asedelivery.backend.model.Delivery;

import jakarta.mail.MessagingException;

public class NewOrderEmail extends Email {

    public NewOrderEmail(
        JavaMailSender emailSender,
        SpringTemplateEngine templateEngine,
        String from,
        String email,
        String name,
        Delivery delivery,
        String deliveryURL
    ) throws MessagingException {
        super(emailSender, templateEngine, "new-order", from, email,
            "Your new order " + delivery.getId() + " has been ordered");
        set("name", name);
        set("email", email);
        
        set("deliveryId", delivery.getId());
        set("pickupAddress", delivery.pickupAddress);
        set("boxAddress", delivery.box.address);

        set("deliveryURL", deliveryURL);
    }
}
