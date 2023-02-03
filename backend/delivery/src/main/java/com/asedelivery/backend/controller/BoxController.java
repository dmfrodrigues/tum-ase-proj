package com.asedelivery.backend.controller;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.asedelivery.backend.email.Email;
import com.asedelivery.backend.model.Box;
import com.asedelivery.backend.model.Delivery;
import com.asedelivery.backend.model.repo.BoxRepository;
import com.asedelivery.backend.model.repo.DeliveryRepository;
import com.asedelivery.backend.service.AuthServiceDelivery;
import com.asedelivery.backend.service.EmailService;
import com.asedelivery.common.auth.jwt.JwtUtil;
import com.asedelivery.common.model.Role;

import jakarta.mail.MessagingException;

@RestController
@RequestMapping("/box")
public class BoxController {
    @Autowired
    BoxRepository boxRepo;

    @Autowired
    DeliveryRepository deliveryRepo;

    @Autowired
    AuthServiceDelivery authService;

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    EmailService emailService;

    @GetMapping("")
    @PreAuthorize("hasRole('" + Role.DISPATCHER_STR + "')")
    public List<Box> getBoxes() {
        return boxRepo.findAll();
    }

    @PutMapping("")
    @PreAuthorize("hasRole('" + Role.DISPATCHER_STR + "')")
    public Box putBox(
            @CookieValue("jwt") String jwt,
            @RequestParam(value = "username") String username,
            @RequestParam(value = "password") String password,
            @RequestParam(value = "address") String address) {
        Box ret = boxRepo.save(new Box(username, address));
        authService.putPrincipal(jwt, ret.getId(), ret.getRole(), username, password);
        return ret;
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('" + Role.DISPATCHER_STR + "')")
    public void delCustomer(@PathVariable String id) {
        if (!boxRepo.existsById(id))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        else
            boxRepo.deleteById(id);
    }

    @PostMapping("/{id}/open")
    @PreAuthorize("hasRole('" + Role.BOX_STR + "')")
    public Boolean canOpenBox(
        @RequestParam(value = "token") String token
    ){
        ResponseEntity<String> response = authService.authenticateUserWithApiToken(token);
        if(response.getStatusCode() != HttpStatus.OK) return false;

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String boxId = auth.getName();
        Box box = boxRepo.findById(boxId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR));

        String jwt = response.getBody();
        String userId = jwtUtil.extractUsername(jwt);
        Collection<? extends GrantedAuthority> authorities = jwtUtil.extractUserRoles(jwt);
        String authority = authorities.iterator().next().getAuthority();
        switch(authority){
            case "ROLE_" + Role.DELIVERER_STR:
                {
                    List<Delivery> deliveries = deliveryRepo
                        .findByBoxIdAndDelivererIdAndState(boxId, userId, Delivery.State.PICKED_UP);
                    if(deliveries.size() <= 0) return false;
                    return true;
                }
            case "ROLE_" + Role.CUSTOMER_STR:
                if(!box.customer.getId().equals(userId)) return false;
                {
                    List<Delivery> deliveries = deliveryRepo
                        .findByBoxIdAndCustomerIdAndState(boxId, userId, Delivery.State.DELIVERED);
                    if(deliveries.size() <= 0) return false;
                    for(Delivery delivery: deliveries){
                        delivery.advanceState();
                    }
                    deliveries = deliveryRepo.saveAll(deliveries);
                    for(Delivery delivery: deliveries){
                        try {
                            Email email = emailService.createCompletedDeliveredEmail(
                                delivery.customer.email, delivery.customer.name, delivery);
                            email.send();
                        } catch (MessagingException e) {
                            System.err.println("Failed to send completed delivery email to " + delivery.customer.email);
                            e.printStackTrace();
                        }
                    }
                    return true;
                }
            default:
                return false;
        }
    }
}
