package com.asedelivery.backend.controller;

import java.util.List;
import java.util.Optional;
import java.util.SortedSet;

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

import com.asedelivery.backend.email.Email;
import com.asedelivery.backend.model.Agent;
import com.asedelivery.backend.model.Box;
import com.asedelivery.backend.model.Customer;
import com.asedelivery.backend.model.Deliverer;
import com.asedelivery.backend.model.Delivery;
import com.asedelivery.backend.model.Dispatcher;
import com.asedelivery.backend.model.Principal;
import com.asedelivery.backend.model.Principal.Role;
import com.asedelivery.backend.model.repo.AgentRepository;
import com.asedelivery.backend.model.repo.BoxRepository;
import com.asedelivery.backend.model.repo.CustomerRepository;
import com.asedelivery.backend.model.repo.DelivererRepository;
import com.asedelivery.backend.model.repo.DeliveryRepository;
import com.asedelivery.backend.model.repo.DispatcherRepository;
import com.asedelivery.backend.service.EmailService;

import jakarta.mail.MessagingException;

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

    @Autowired
    EmailService emailService;

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
        Customer customer = customerRepo.findById(customerId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Customer not found"));
        Dispatcher dispatcher = dispatcherRepo.findById(createdById)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Dispatcher not found"));
        Deliverer deliverer = delivererRepo.findById(delivererId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Deliverer not found"));
        Box box = boxRepo.findById(boxId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Box not found"));

        Delivery delivery = deliveryRepo.save(
            new Delivery(
                customer,
                dispatcher,
                deliverer,
                pickupAddress,
                box
            )
        );

        try {
            Email mail = emailService.createNewDeliveryEmail(
                delivery.customer.email,
                delivery.customer.name,
                delivery
            );
            mail.send();
        } catch(MessagingException e){
            System.err.println("Failed to send new order email to " + customer.email);
        }
        
        return delivery;
    }

    @PatchMapping("/{id}")
    @PostAuthorize(
        "hasRole('" + Principal.Role.DISPATCHER_STR + "') or " +
        "hasRole('" + Principal.Role.DELIVERER_STR  + "')"
    )
    public Delivery patchDelivery(
        @PathVariable String id,
        @RequestParam(value = "customerId") Optional<String> customerId,
        @RequestParam(value = "createdById") Optional<String> createdById,
        @RequestParam(value = "delivererId") Optional<String> delivererId,
        @RequestParam(value = "pickupAddress") Optional<String> pickupAddress,
        @RequestParam(value = "boxId") Optional<String> boxId,
        @RequestParam(value = "state") Optional<Delivery.State> state,
        @RequestParam(value = "events") Optional<SortedSet<Delivery.Event>> events
    ){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String authority = auth.getAuthorities().iterator().next().getAuthority();

        Delivery delivery = deliveryRepo.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if(!delivery.deliverer.getId().equals(auth.getName()))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);

        try {
            if(customerId.isPresent()){
                if(!authority.equals("ROLE_" + Role.DISPATCHER_STR))
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Only DISPATCHER can change customerId");
                delivery.customer = customerRepo.findById(customerId.get())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Customer not found"));
            }
            if(createdById.isPresent()){
                if(!authority.equals("ROLE_" + Role.DISPATCHER_STR))
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Only DISPATCHER can change createdById");
                delivery.createdBy = dispatcherRepo.findById(createdById.get())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Dispatcher not found"));
            }
            if(delivererId.isPresent()){
                if(!authority.equals("ROLE_" + Role.DISPATCHER_STR))
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Only DISPATCHER can change delivererId");
                delivery.deliverer = delivererRepo.findById(delivererId.get())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Deliverer not found"));
            }
            if(pickupAddress.isPresent()){
                if(!authority.equals("ROLE_" + Role.DISPATCHER_STR))
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Only DISPATCHER can change pickupAddress");
                delivery.pickupAddress = pickupAddress.get();
            }
            if(boxId.isPresent()){
                if(!authority.equals("ROLE_" + Role.DISPATCHER_STR))
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Only DISPATCHER can change boxId");
                delivery.box = boxRepo.findById(boxId.get())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Box not found"));
            }
            if(state.isPresent() && events.isPresent()){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Only one of state or events can be defined");
            }
            if(state.isPresent()){
                try {
                    if(state.get() != delivery.getState().next())
                        throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                            "Delivery state " + delivery.getState() +
                            " can only be advanced to " + delivery.getState().next() +
                            ", not to " + state.get()
                        );
                } catch(IllegalStateException e){
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
                }
                delivery.advanceState();
            }
            if(events.isPresent()){
                if(!authority.equals("ROLE_" + Role.DISPATCHER_STR))
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Only DISPATCHER can change events");
                delivery.setEvents(events.get());
            }
        } catch(IllegalStateException | IllegalArgumentException e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }

        delivery = deliveryRepo.save(delivery);

        try {
            if(
                state.isPresent() ||
                events.isPresent()
            ){
                Email mail = null;
                if(delivery.getState() == Delivery.State.DELIVERED){
                    mail = emailService.createDeliveryDeliveredEmail(
                        delivery.customer.email,
                        delivery.customer.name,
                        delivery
                    );
                } else if(delivery.getState() == Delivery.State.COMPLETED){
                    mail = emailService.createCompletedDeliveredEmail(
                        delivery.customer.email,
                        delivery.customer.name,
                        delivery
                    );
                }
                if(mail != null) mail.send();
            }
        } catch(MessagingException e){
            System.err.println("Failed to send delivered delivery email to " + delivery.customer.email);
        }

        return delivery;
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('" + Principal.Role.DISPATCHER_STR + "')")
    public void delDelivery(@PathVariable String id) {
        deliveryRepo.deleteById(id);
    }
}
