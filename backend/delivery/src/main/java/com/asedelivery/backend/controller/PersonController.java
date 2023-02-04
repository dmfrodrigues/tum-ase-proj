package com.asedelivery.backend.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.asedelivery.backend.email.Email;
import com.asedelivery.backend.model.Customer;
import com.asedelivery.backend.model.Person;
import com.asedelivery.backend.model.repo.PersonRepository;
import com.asedelivery.backend.service.AuthServiceDelivery;
import com.asedelivery.backend.service.EmailService;
import com.asedelivery.common.model.Role;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;

@Tag(name="Person")
@RestController
@RequestMapping("/api/person")
public class PersonController {

    @Autowired
    PersonRepository personRepo;

    @Autowired
    EmailService emailService;

    @Autowired
    AuthServiceDelivery authService;
    
    @Operation(summary="Modify customer")
    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('" + Role.DISPATCHER_STR + "')")
    public Person patchCustomer(
        @CookieValue("jwt") @Parameter(description="JWT token") String jwt,
        @PathVariable @Parameter(description="Customer ID") String customerId,
        @RequestParam(value = "username") @Parameter(description="Customer username") Optional<String> username,
        @RequestParam(value = "password") @Parameter(description="Customer password") Optional<String> password,
        @RequestParam(value = "name") @Parameter(description="Customer name") Optional<String> name,
        @RequestParam(value = "email") @Parameter(description="Customer email address") Optional<String> email
    ){
        Person ret = personRepo.findByIdAndClass(customerId, Customer.class.getName())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        String oldUsername = ret.username;
        String oldName = ret.name;
        String oldEmail = ret.email;
        String newPassword = "(not modified)";

        if(username.isPresent()){ ret.username = username.get(); }
        if(name    .isPresent()){ ret.name     = name    .get(); }
        if(email   .isPresent()){ ret.email    = email   .get(); }
        if(password.isPresent()){
            newPassword = password.get();
            authService.putPrincipal(jwt, ret.getId(), ret.getRole(), ret.username, password.get());
        }

        if(
            !ret.username.equals(oldUsername) ||
            !ret.name    .equals(oldName    ) ||
            !ret.email   .equals(oldEmail   ) ||
            password.isPresent()
        ){
            ret = personRepo.save(ret);

            try {
                Email mail = emailService.createModifiedPersonEmail(
                    oldEmail,
                    oldName,
                    oldUsername, ret.username,
                    oldName, ret.name,
                    oldEmail, ret.email,
                    newPassword
                );
                mail.send();
            } catch (MessagingException e) {
                System.err.println("Failed to send modification email to " + email);
                e.printStackTrace();
            }
        }

        return ret;
    }
}
