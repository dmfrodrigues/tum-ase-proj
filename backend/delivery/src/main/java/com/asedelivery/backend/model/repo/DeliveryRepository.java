package com.asedelivery.backend.model.repo;

import java.util.List;

import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;

import com.asedelivery.backend.model.Box;
import com.asedelivery.backend.model.Customer;
import com.asedelivery.backend.model.Deliverer;
import com.asedelivery.backend.model.Delivery;
import com.asedelivery.backend.model.Delivery.State;

public interface DeliveryRepository extends MongoRepository<Delivery, String> {

    List<Delivery> findByDeliverer(Deliverer deliverer);

    List<Delivery> findByCustomer(Customer customer);

    List<Delivery> findByBoxAndCustomer(Box box, Customer customer);

    @Aggregation(pipeline={
        "{ $addFields: { lastEvent: { $last: '$events'}}}",
        "{ $match: { 'box': :#{#box}, 'deliverer': :#{#deliverer}, 'lastEvent.state': :#{#state}}}",
        "{ $project: { 'lastEvent': 0 } }"
    })
    List<Delivery> findByBoxAndDelivererAndState(
        @Param("box") Box box,
        @Param("deliverer") Deliverer deliverer,
        @Param("state") State state
    );

    @Aggregation(pipeline={
        "{ $addFields: { lastEvent: { $last: '$events'}}}",
        "{ $match: { 'box': :#{#box}, 'customer': :#{#customer}, 'lastEvent.state': :#{#state}}}",
        "{ $project: { 'lastEvent': 0 } }"
    })
    List<Delivery> findByBoxAndCustomerAndState(
        @Param("box") Box box,
        @Param("customer") Customer customer,
        @Param("state") State state
    );
}
