package com.asedelivery.backend.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.asedelivery.backend.Models.Agent;
import com.asedelivery.backend.Models.Customer;
import com.asedelivery.backend.Models.Deliverer;
import com.asedelivery.backend.Models.Delivery;
import com.asedelivery.backend.Models.Dispatcher;
import com.asedelivery.backend.Models.Principal;
import com.asedelivery.backend.Models.Principal.Role;
import com.asedelivery.backend.Models.Repositories.AgentRepository;
import com.asedelivery.backend.Models.Repositories.BoxRepository;
import com.asedelivery.backend.Models.Repositories.CustomerRepository;
import com.asedelivery.backend.Models.Repositories.DelivererRepository;
import com.asedelivery.backend.Models.Repositories.DeliveryRepository;
import com.asedelivery.backend.Models.Repositories.DispatcherRepository;
import com.fasterxml.jackson.annotation.JsonView;

@RestController
@RequestMapping("/delivery")
public class DeliveryController {
    @Autowired
    DeliveryRepository deliveryRepo;

    @Autowired
    CustomerRepository customerRepo;

    @Autowired
    DispatcherRepository dispatcherRepo;

    @Autowired
    DelivererRepository delivererRepo;

    @Autowired
    BoxRepository boxRepo;

    @Autowired
    AgentRepository agentRepo;

    @GetMapping("")
    public List<Delivery> getDeliveries() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String id = auth.getName();
        System.out.println("id=" + id);

        Agent agent = agentRepo.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR));

        String authority = auth.getAuthorities().iterator().next().getAuthority();
        switch(authority){
            case "ROLE_" + Role.DISPATCHER_STR:
                return deliveryRepo.findAll();
            
            case "ROLE_" + Role.DELIVERER_STR:
                return deliveryRepo.findByDeliverer((Deliverer)agent);
            
            case "ROLE_" + Role.CUSTOMER_STR:
                return deliveryRepo.findByCustomer((Customer)agent);
            
            default:
                throw new IllegalStateException("Logged in user must have authority");
        }
    }

    @GetMapping("/{id}")
    @PostAuthorize(
        "hasRole('" + Principal.Role.DISPATCHER_STR + "') or " +
        "(" + 
            "hasRole('" + Principal.Role.DELIVERER_STR + "') and " +
            "principal.username == returnObject.getDeliverer().getId()" +
        ") or (" +
            "hasRole('" + Principal.Role.CUSTOMER_STR + "') and " +
            "principal.username == returnObject.getCustomer().getId()" +
        ")"
    )
    public Delivery getDeliveryById(@PathVariable String id) {
        Delivery delivery = deliveryRepo.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return delivery;
    }

    @PutMapping("")
    @PreAuthorize("hasRole('" + Principal.Role.DISPATCHER_STR + "')")
    public Delivery putDelivery(
            @RequestParam(value = "customerId") String customerId,
            @RequestParam(value = "createdById") String createdById,
            @RequestParam(value = "delivererId") String delivererId,
            @RequestParam(value = "pickupAddress") String pickupAddress,
            @RequestParam(value = "boxId") String boxId) {
        return deliveryRepo.save(
                new Delivery(
                        customerRepo.findById(customerId)
                                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND)),
                        dispatcherRepo.findById(createdById)
                                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND)),
                        delivererRepo.findById(delivererId)
                                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND)),
                        pickupAddress,
                        boxRepo.findById(boxId)
                                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND))));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('" + Principal.Role.DISPATCHER_STR + "')")
    public void delDelivery(@PathVariable String id) {
        deliveryRepo.deleteById(id);
    }
}
