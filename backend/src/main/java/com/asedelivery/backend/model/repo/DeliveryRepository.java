package com.asedelivery.backend.model.repo;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.asedelivery.backend.model.Customer;
import com.asedelivery.backend.model.Deliverer;
import com.asedelivery.backend.model.Delivery;

public interface DeliveryRepository extends MongoRepository<Delivery, String> {

    List<Delivery> findByDeliverer(Deliverer deliverer);

    List<Delivery> findByCustomer(Customer customer);

}
