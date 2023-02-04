package com.asedelivery.backend.model.repo;

import java.util.List;

import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;

import com.asedelivery.backend.model.Customer;
import com.asedelivery.backend.model.Deliverer;
import com.asedelivery.backend.model.Delivery;
import com.asedelivery.backend.model.Delivery.State;

public interface DeliveryRepository extends MongoRepository<Delivery, String> {

    List<Delivery> findByDeliverer(Deliverer deliverer);

    List<Delivery> findByCustomer(Customer customer);

    List<Delivery> findByBoxIdAndCustomerId(String boxId, String userId);

    @Aggregation(pipeline={
        "{ $addFields: { lastEvent: { $last: '$events'}}}",
        "{ $match: { 'box': ObjectId(:#{#boxId}), 'deliverer': ObjectId(:#{#delivererId}), 'lastEvent.state': :#{#state}}}",
        "{ $project: { 'lastEvent': 0 } }"
    })
    List<Delivery> findByBoxIdAndDelivererIdAndState(
        @Param("boxId") String boxId,
        @Param("delivererId") String delivererId,
        @Param("state") State state
    );

    @Aggregation(pipeline={
        "{ $addFields: { lastEvent: { $last: '$events'}}}",
        "{ $match: { 'box': ObjectId(:#{#boxId}), 'customer': ObjectId(:#{#customerId}), 'lastEvent.state': :#{#state}}}",
        "{ $project: { 'lastEvent': 0 } }"
    })
    List<Delivery> findByBoxIdAndCustomerIdAndState(
        @Param("boxId") String boxId,
        @Param("customerId") String customerId,
        @Param("state") State state
    );
}
