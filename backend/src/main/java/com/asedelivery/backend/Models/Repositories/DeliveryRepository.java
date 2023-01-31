package com.asedelivery.backend.Models.Repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.asedelivery.backend.Models.Customer;
import com.asedelivery.backend.Models.Deliverer;
import com.asedelivery.backend.Models.Delivery;

public interface DeliveryRepository extends MongoRepository<Delivery, String> {

    List<Delivery> findByDeliverer(Deliverer deliverer);

    List<Delivery> findByCustomer(Customer customer);

}
