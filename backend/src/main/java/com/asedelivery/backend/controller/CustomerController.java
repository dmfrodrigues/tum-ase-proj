package com.asedelivery.backend.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.asedelivery.backend.email.Email;
import com.asedelivery.backend.model.Customer;
import com.asedelivery.backend.model.Principal;
import com.asedelivery.backend.model.repo.CustomerRepository;
import com.asedelivery.backend.model.repo.PrincipalRepository;
import com.asedelivery.backend.service.EmailService;

import jakarta.mail.MessagingException;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    CustomerRepository customerRepo;

    @Autowired
    PrincipalRepository principalRepo;

    @Autowired
    EmailService emailService;

    @GetMapping("")
    @PreAuthorize("hasRole('" + Principal.Role.DISPATCHER_STR + "')")
    public List<Customer> getCustomer() {
        return customerRepo.findAll();
    }

    @PutMapping("")
    @PreAuthorize("hasRole('" + Principal.Role.DISPATCHER_STR + "')")
    public Customer putCustomer(
            @RequestParam(value = "username") String username,
            @RequestParam(value = "password") String password,
            @RequestParam(value = "name") String name,
            @RequestParam(value = "email") String email) {
        Customer ret = customerRepo.save(new Customer(username, name, email));
        principalRepo.save(new Principal(ret.getId(), ret.getRole(), username, password));

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
    @PreAuthorize("hasRole('" + Principal.Role.DISPATCHER_STR + "')")
    public void delCustomer(@PathVariable String id) {
        if (!customerRepo.existsById(id))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        else
            customerRepo.deleteById(id);
    }
}
