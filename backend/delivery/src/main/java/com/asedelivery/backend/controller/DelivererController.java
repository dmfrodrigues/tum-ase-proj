package com.asedelivery.backend.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.asedelivery.backend.email.Email;
import com.asedelivery.backend.model.Deliverer;
import com.asedelivery.backend.model.repo.DelivererRepository;
import com.asedelivery.backend.service.AuthServiceDelivery;
import com.asedelivery.backend.service.EmailService;
import com.asedelivery.common.model.Role;

import jakarta.mail.MessagingException;

@RestController
@RequestMapping("/deliverer")
public class DelivererController {

    @Autowired
    DelivererRepository delivererRepo;

    @Autowired
    AuthServiceDelivery authService;

    @Autowired
    EmailService emailService;

    @GetMapping("")
    @PreAuthorize("hasRole('" + Role.DISPATCHER_STR + "')")
    public List<Deliverer> getDeliverer() {
        return delivererRepo.findAll();
    }

    @PutMapping("")
    @PreAuthorize("hasRole('" + Role.DISPATCHER_STR + "')")
    public Deliverer putDeliverer(
            @CookieValue("jwt") String jwt,
            @RequestParam(value = "username") String username,
            @RequestParam(value = "password") String password,
            @RequestParam(value = "name") String name,
            @RequestParam(value = "email") String email) {
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

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('" + Role.DISPATCHER_STR + "')")
    public void delDeliverer(@PathVariable String id) {
        if (!delivererRepo.existsById(id))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        else
            delivererRepo.deleteById(id);
    }
}
