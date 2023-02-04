package com.asedelivery.backend.controller;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;

@Tag(name="6. Box")
@RestController
@RequestMapping("/api/box")
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

    @Operation(summary="Get all boxes")
    @GetMapping("")
    @PreAuthorize("hasRole('" + Role.DISPATCHER_STR + "')")
    public List<Box> getBoxes() {
        return boxRepo.findByClass(Box.class.getName());
    }

    @Operation(summary="Create box")
    @PutMapping("")
    @PreAuthorize("hasRole('" + Role.DISPATCHER_STR + "')")
    public Box putBox(
        @CookieValue("jwt") @Parameter(description="JWT token") String jwt,
        @RequestParam(value = "username") @Parameter(description="New box username") String username,
        @RequestParam(value = "password") @Parameter(description="New box password") String password,
        @RequestParam(value = "address") @Parameter(description="New box address") String address
    ){
        Box ret = boxRepo.save(new Box(username, address));
        authService.putPrincipal(jwt, ret.getId(), ret.getRole(), username, password);
        return ret;
    }

    @Operation(summary="Modify box")
    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('" + Role.DISPATCHER_STR + "')")
    public Box patchBox(
        @CookieValue("jwt") @Parameter(description="JWT token") String jwt,
        @PathVariable @Parameter(description="Box ID") String boxId,
        @RequestParam(value = "username") @Parameter(description="Box username") Optional<String> username,
        @RequestParam(value = "password") @Parameter(description="Box password") Optional<String> password,
        @RequestParam(value = "address") @Parameter(description="Box address") Optional<String> address
    ){
        Box ret = boxRepo.findByIdAndClass(boxId, Box.class.getName())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        boolean modified = false;
        if(username.isPresent()){ ret.username = username.get(); modified = true; }
        if(address .isPresent()){ ret.address  = address .get(); modified = true; }
        if(modified) ret = boxRepo.save(ret);

        if(password.isPresent())
            authService.putPrincipal(jwt, ret.getId(), ret.getRole(), ret.username, password.get());

        return ret;
    }

    @Operation(summary="Open a box")
    @ApiResponses(value = { 
        @ApiResponse(
            responseCode = "200",
            description = "Boolean, indicating if the box can be opened or not"
        )
    })
    @PostMapping("/{id}/open")
    @PreAuthorize(
        "hasRole('" + Role.BOX_STR + "') and" +
        "principal.username == #boxId"
    )
    public Boolean canOpenBox(
        @PathVariable @Parameter(description="Box ID") String boxId,
        @RequestParam(value = "token") @Parameter(description="Token of user trying to open the box") String token
    ){
        ResponseEntity<String> response = authService.authenticateUserWithApiToken(token);
        if(response.getStatusCode() != HttpStatus.OK) return false;
  
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

                    /* If all deliveries of the client in that box have been
                     * delivered, this box no longer belongs to this user.
                     */
                    if(allDeliveriesCompleted(box)){
                        box.customer = null;
                        box = boxRepo.save(box);
                    }

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

    private boolean allDeliveriesCompleted(Box box){
        List<Delivery> boxDeliveries = deliveryRepo
            .findByBoxAndCustomer(box, box.customer);
        return boxDeliveries.stream()
            .anyMatch((delivery) -> delivery.getState() == Delivery.State.COMPLETED);
    }
}
