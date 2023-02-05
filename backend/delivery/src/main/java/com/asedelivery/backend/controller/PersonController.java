package com.asedelivery.backend.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.asedelivery.backend.email.Email;
import com.asedelivery.backend.model.Person;
import com.asedelivery.backend.model.repo.AgentRepository;
import com.asedelivery.backend.service.AuthServiceDelivery;
import com.asedelivery.backend.service.EmailService;
import com.asedelivery.common.model.Role;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;

@Tag(name="2. Person")
@RestController
@RequestMapping("/api/person")
public class PersonController {

    @Autowired
    AgentRepository agentRepo;

    @Autowired
    EmailService emailService;

    @Autowired
    AuthServiceDelivery authService;

    @Operation(summary="Get all persons")
    @GetMapping("")
    @PreAuthorize("hasRole('" + Role.DISPATCHER_STR + "')")
    public List<Person> getPerson() {
        return agentRepo
            .findAll()
            .stream()
            .filter((a) -> a instanceof Person)
            .map((a) -> (Person)a)
            .collect(Collectors.toList())
        ;
    }
    
    @Operation(summary="Modify person")
    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('" + Role.DISPATCHER_STR + "')")
    public Person patchPerson(
        @CookieValue("jwt") @Parameter(description="JWT token") String jwt,
        @PathVariable @Parameter(description="Person ID") String id,
        @RequestParam(value = "username") @Parameter(description="Person username") Optional<String> username,
        @RequestParam(value = "password") @Parameter(description="Person password") Optional<String> password,
        @RequestParam(value = "name") @Parameter(description="Person name") Optional<String> name,
        @RequestParam(value = "email") @Parameter(description="Person email address") Optional<String> email
    ){
        Person ret = (Person)agentRepo.findById(id)
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
            ret = agentRepo.save(ret);

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
