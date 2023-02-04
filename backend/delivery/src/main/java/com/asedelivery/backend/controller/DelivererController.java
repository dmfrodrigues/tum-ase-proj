package com.asedelivery.backend.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.asedelivery.backend.email.Email;
import com.asedelivery.backend.model.Deliverer;
import com.asedelivery.backend.model.repo.DelivererRepository;
import com.asedelivery.backend.service.AuthServiceDelivery;
import com.asedelivery.backend.service.EmailService;
import com.asedelivery.common.model.Role;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;

@Tag(name="Deliverer")
@RestController
@RequestMapping("/api/deliverer")
public class DelivererController {

    @Autowired
    DelivererRepository delivererRepo;

    @Autowired
    AuthServiceDelivery authService;

    @Autowired
    EmailService emailService;

    @Operation(summary="Get all deliverers")
    @GetMapping("")
    @PreAuthorize("hasRole('" + Role.DISPATCHER_STR + "')")
    public List<Deliverer> getDeliverer() {
        return delivererRepo.findByClass(Deliverer.class.getName());
    }

    @Operation(summary="Create deliverer")
    @PutMapping("")
    @PreAuthorize("hasRole('" + Role.DISPATCHER_STR + "')")
    public Deliverer putDeliverer(
            @CookieValue("jwt") String jwt,
            @RequestParam(value = "username") @Parameter(description="New deliverer name") String username,
            @RequestParam(value = "password") @Parameter(description="New deliverer password") String password,
            @RequestParam(value = "name") @Parameter(description="New deliverer name") String name,
            @RequestParam(value = "email") @Parameter(description="New deliverer email address") String email) {
        Deliverer ret = delivererRepo.save(new Deliverer(username, name, email));
        authService.putPrincipal(jwt, ret.getId(), ret.getRole(), username, password);

        try {
            Email mail = emailService.createRegistrationEmail(
                email,
                name,
                username,
                password
            );
            mail.send();
        } catch(MessagingException e){
            System.err.println("Failed to send registration email to " + email);
            e.printStackTrace();
        }

        return ret;
    }
}
