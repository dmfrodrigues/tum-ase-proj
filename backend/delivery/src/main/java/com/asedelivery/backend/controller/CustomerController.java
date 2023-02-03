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
import com.asedelivery.backend.model.Customer;
import com.asedelivery.backend.model.repo.CustomerRepository;
import com.asedelivery.backend.service.AuthServiceDelivery;
import com.asedelivery.backend.service.EmailService;
import com.asedelivery.common.model.Role;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;

@Tag(name="Customer")
@RestController
@RequestMapping("/api/customer")
public class CustomerController {

    @Autowired
    CustomerRepository customerRepo;

    @Autowired
    EmailService emailService;

    @Autowired
    AuthServiceDelivery authService;

    @Operation(summary="Get all customers")
    @GetMapping("")
    @PreAuthorize("hasRole('" + Role.DISPATCHER_STR + "')")
    public List<Customer> getCustomer() {
        return customerRepo.findAll();
    }

    @Operation(summary="Create customer")
    @PutMapping("")
    @PreAuthorize("hasRole('" + Role.DISPATCHER_STR + "')")
    public Customer putCustomer(
        @CookieValue("jwt") String jwt,
        @RequestParam(value = "username") @Parameter(description="New customer username") String username,
        @RequestParam(value = "password") @Parameter(description="New customer password") String password,
        @RequestParam(value = "name") @Parameter(description="New customer name") String name,
        @RequestParam(value = "email") @Parameter(description="New customer email address") String email
    ) {
        Customer ret = customerRepo.save(new Customer(username, name, email));
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
