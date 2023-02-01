package com.asedelivery.backend.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.asedelivery.backend.model.Agent;
import com.asedelivery.backend.model.Customer;
import com.asedelivery.backend.model.Deliverer;
import com.asedelivery.backend.model.Delivery;
import com.asedelivery.backend.model.Principal;
import com.asedelivery.backend.model.Principal.Role;
import com.asedelivery.backend.model.repo.AgentRepository;
import com.asedelivery.backend.model.repo.BoxRepository;
import com.asedelivery.backend.model.repo.CustomerRepository;
import com.asedelivery.backend.model.repo.DelivererRepository;
import com.asedelivery.backend.model.repo.DeliveryRepository;
import com.asedelivery.backend.model.repo.DispatcherRepository;

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

        Agent agent = agentRepo.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.FORBIDDEN));

        String authority = auth.getAuthorities().iterator().next().getAuthority();
        switch(authority){
            case "ROLE_" + Role.DISPATCHER_STR:
                return deliveryRepo.findAll();
            
            case "ROLE_" + Role.DELIVERER_STR:
                return deliveryRepo.findByDeliverer((Deliverer)agent);
            
            case "ROLE_" + Role.CUSTOMER_STR:
                return deliveryRepo.findByCustomer((Customer)agent);
            
            default:
                throw new IllegalStateException("Invalid authority " + authority);
        }
    }

    @GetMapping("/{id}")
    @PostAuthorize(
        "hasRole('" + Principal.Role.DISPATCHER_STR + "') or " +
        "(" + 
            "hasRole('" + Principal.Role.DELIVERER_STR + "') and " +
            "principal.username == returnObject.deliverer.getId()" +
        ") or (" +
            "hasRole('" + Principal.Role.CUSTOMER_STR + "') and " +
            "principal.username == returnObject.customer.getId()" +
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
        @RequestParam(value = "boxId") String boxId
    ) {
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

    @Transactional
    @PatchMapping("/{id}")
    @PostAuthorize(
        "hasRole('" + Principal.Role.DISPATCHER_STR + "') or " +
        "(" + 
            "hasRole('" + Principal.Role.DELIVERER_STR + "') and " +
            "principal.username == returnObject.deliverer.getId()" +
        ")"
    )
    public Delivery patchDelivery(
        @PathVariable String id,
        @RequestParam(value = "customerId") Optional<String> customerId,
        @RequestParam(value = "createdById") Optional<String> createdById,
        @RequestParam(value = "delivererId") Optional<String> delivererId,
        @RequestParam(value = "pickupAddress") Optional<String> pickupAddress,
        @RequestParam(value = "boxId") Optional<String> boxId
    ){
        Delivery delivery = deliveryRepo.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        
        if(customerId.isPresent()) delivery.customer = customerRepo.findById(customerId.get())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        if(createdById.isPresent()) delivery.createdBy = dispatcherRepo.findById(createdById.get())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        if(delivererId.isPresent()) delivery.deliverer = delivererRepo.findById(delivererId.get())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        if(pickupAddress.isPresent()) delivery.pickupAddress = pickupAddress.get();
        if(boxId.isPresent()) delivery.box = boxRepo.findById(boxId.get())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        delivery = deliveryRepo.save(delivery);
    
        return delivery;
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('" + Principal.Role.DISPATCHER_STR + "')")
    public void delDelivery(@PathVariable String id) {
        deliveryRepo.deleteById(id);
    }
}
