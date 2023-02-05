package com.asedelivery.backend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.asedelivery.backend.model.Box;
import com.asedelivery.backend.model.Delivery;
import com.asedelivery.backend.model.repo.DeliveryRepository;

@Service
public class BoxService {

    @Autowired
    DeliveryRepository deliveryRepo;

    public boolean allDeliveriesCompleted(Box box){
        List<Delivery> boxDeliveries = deliveryRepo
            .findByBoxAndCustomer(box, box.customer);
        return boxDeliveries.stream()
            .allMatch((delivery) -> delivery.getState() == Delivery.State.COMPLETED);
    }
}
