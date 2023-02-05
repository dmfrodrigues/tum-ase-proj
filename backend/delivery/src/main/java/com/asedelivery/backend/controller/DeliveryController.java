package com.asedelivery.backend.controller;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.SortedSet;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
import com.asedelivery.backend.model.repo.AgentRepository;
import com.asedelivery.backend.model.repo.BoxRepository;
import com.asedelivery.backend.model.repo.CustomerRepository;
import com.asedelivery.backend.model.repo.DelivererRepository;
import com.asedelivery.backend.model.repo.DeliveryRepository;
import com.asedelivery.backend.model.repo.DispatcherRepository;
import com.asedelivery.backend.service.BoxService;
import com.asedelivery.backend.service.EmailService;
import com.asedelivery.common.model.Role;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;

@Tag(name="7. Delivery")
@RestController
@RequestMapping("/api/delivery")
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

    @Autowired
    BoxService boxService;

    @Operation(summary="Get all deliveries")
    @GetMapping("")
    @PreAuthorize(
        "hasRole('" + Role.DISPATCHER_STR + "') or " +
        "hasRole('" + Role.DELIVERER_STR  + "') or " +
        "hasRole('" + Role.CUSTOMER_STR   + "')"
    )
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

    @Operation(summary="Get delivery")
    @GetMapping("/{id}")
    public Delivery getDeliveryById(
        @PathVariable @Parameter(description="Delivery ID") String id
    ) {
        Delivery delivery = deliveryRepo.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return delivery;
    }

    @Operation(summary="Create delivery")
    @PutMapping("")
    @PreAuthorize(
        "hasRole('" + Role.DISPATCHER_STR + "') and " +
        "principal == #createdById"
    )
    public Delivery putDelivery(
        @RequestParam(value = "customerId") @Parameter(description="Customer that ordered the delivery") String customerId,
        @RequestParam(value = "createdById") @Parameter(description="Dispatcher that created the delivery") String createdById,
        @RequestParam(value = "delivererId") @Parameter(description="Deliverer responsible for the delivery") String delivererId,
        @RequestParam(value = "pickupAddress") @Parameter(description="Delivery pick-up address") String pickupAddress,
        @RequestParam(value = "boxId") @Parameter(description="Box where delivery will be delivered") String boxId
    ) {
        Customer customer = customerRepo.findById(customerId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Customer not found"));
        Dispatcher dispatcher = dispatcherRepo.findById(createdById)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Dispatcher not found"));
        Deliverer deliverer = delivererRepo.findById(delivererId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Deliverer not found"));
        Box box = boxRepo.findById(boxId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Box not found"));

        if(box.customer == null){
            box.customer = customer;
            box = boxRepo.save(box);
        } else if(!customer.equals(box.customer)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Box is already taken by another customer");
        }

        Delivery delivery = new Delivery(
            customer,
            dispatcher,
            deliverer,
            pickupAddress,
            box
        );
        SortedSet<Delivery.Event> events = new TreeSet<>(){{
            add(new Delivery.Event(new Date(), Delivery.State.ORDERED));
        }};
        delivery.setEvents(events);
        delivery = deliveryRepo.save(delivery);

        try {
            Email mail = emailService.createNewDeliveryEmail(
                delivery.customer.email,
                delivery.customer.name,
                delivery
            );
            mail.send();
        } catch(MessagingException e){
            System.err.println("Failed to send new order email to " + customer.email);
            e.printStackTrace();
        }
        
        return delivery;
    }

    @Operation(summary="Modify delivery")
    @PatchMapping("/{id}")
    @PostAuthorize(
        "hasRole('" + Role.DISPATCHER_STR + "') or " +
        "hasRole('" + Role.DELIVERER_STR  + "')"
    )
    public Delivery patchDelivery(
        @PathVariable String id,
        @RequestParam(value = "customerId") @Parameter(description="Customer") Optional<String> customerId,
        @RequestParam(value = "createdById") @Parameter(description="Dispatcher") Optional<String> createdById,
        @RequestParam(value = "delivererId") @Parameter(description="Deliverer") Optional<String> delivererId,
        @RequestParam(value = "pickupAddress") @Parameter(description="Pick-up address") Optional<String> pickupAddress,
        @RequestParam(value = "boxId") @Parameter(description="Box") Optional<String> boxId,
        @RequestParam(value = "state") @Parameter(description="State that the delivery is now on") Optional<Delivery.State> state,
        @RequestParam(value = "events") @Parameter(description="To change the sequence of events of a delivery") Optional<SortedSet<Delivery.Event>> events
    ){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String authority = auth.getAuthorities().iterator().next().getAuthority();

        Delivery delivery = deliveryRepo.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if(
            authority.equals("ROLE_" + Role.DELIVERER_STR) &&
            !delivery.deliverer.getId().equals(auth.getName())
        )
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);

        Box oldBox = delivery.box;
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
            e.printStackTrace();
        }

        Box box = delivery.box;
        if(!oldBox.equals(box)){
            /* If all deliveries of the client in that box have been
             * delivered, this box no longer belongs to this user.
             */
            if(boxService.allDeliveriesCompleted(box)){
                box.customer = null;
                box = boxRepo.save(box);
            }
        }

        return delivery;
    }

    @Operation(summary="Delete delivery")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('" + Role.DISPATCHER_STR + "')")
    public void delDelivery(
        @PathVariable @Parameter(description="Delivery ID") String id
    ) {
        Delivery delivery = deliveryRepo.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        Box box = delivery.box;

        Delivery.State state = delivery.getState();

        deliveryRepo.delete(delivery);

        if(
            state != Delivery.State.COMPLETED &&
            boxService.allDeliveriesCompleted(box)
        ){
            box.customer = null;
            box = boxRepo.save(box);
        }
    }
}
