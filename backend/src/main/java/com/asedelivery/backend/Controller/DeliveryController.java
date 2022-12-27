package com.asedelivery.backend.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.asedelivery.backend.Models.BoxRepository;
import com.asedelivery.backend.Models.CustomerRepository;
import com.asedelivery.backend.Models.DelivererRepository;
import com.asedelivery.backend.Models.Delivery;
import com.asedelivery.backend.Models.DeliveryRepository;
import com.asedelivery.backend.Models.DispatcherRepository;

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

    @GetMapping("")
    public List<Delivery> getDeliveries() {
        return deliveryRepo.findAll();
    }

    @GetMapping("/{id}")
    public Delivery getDeliveryById(@PathVariable String id) {
        return deliveryRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PutMapping("")
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
    public void delCustomer(@PathVariable String id) {
        deliveryRepo.deleteById(id);
    }
}
