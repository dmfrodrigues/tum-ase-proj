package com.asedelivery.backend.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.asedelivery.backend.model.Principal.Role;
import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.BeanPropertyWriter;
import com.fasterxml.jackson.databind.ser.PropertyWriter;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;

@Document("delivery")
@JsonFilter("deliveryPropertyFilter")
public class Delivery {
    @Id
    private String id;

    @DocumentReference
    private Customer customer;

    @DocumentReference
    private Box box;

    @DocumentReference
    private Dispatcher createdBy;

    @DocumentReference
    private Deliverer deliverer;

    private String pickupAddress;

    public Delivery(Customer customer, Dispatcher createdBy, Deliverer deliverer, String pickupAddress, Box box) {
        assert box.getCustomer().equals(customer);

        this.customer = customer;
        this.createdBy = createdBy;
        this.deliverer = deliverer;
        this.pickupAddress = pickupAddress;
        this.box = box;
    }

    public String getId() {
        return id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Box getBox() {
        return box;
    }

    public Dispatcher getCreatedBy() {
        return createdBy;
    }

    public Deliverer getDeliverer() {
        return deliverer;
    }

    public String getPickupAddress() {
        return pickupAddress;
    }

    static public class PropertyFilter extends SimpleBeanPropertyFilter {
        @Override
        public void serializeAsField(
            Object pojo,
            JsonGenerator jgen,
            SerializerProvider provider,
            PropertyWriter writer
        ) throws Exception {
            if (include(writer)) {
                boolean write = false;

                Authentication auth = SecurityContextHolder.getContext().getAuthentication();
                String authority = auth.getAuthorities().iterator().next().getAuthority();

                if(authority.equals("ROLE_" + Role.DISPATCHER_STR)){
                    write = true;
                } else {
                    switch(writer.getName()){
                        case "customer":
                            write = authority.equals("ROLE_" + Role.CUSTOMER_STR);
                            break;
                        case "deliverer":
                            write = authority.equals("ROLE_" + Role.DELIVERER_STR);
                            break;
                        case "box":
                            write =
                                authority.equals("ROLE_" + Role.BOX_STR) ||
                                authority.equals("ROLE_" + Role.DELIVERER_STR);
                            break;
                        case "createdBy":
                            break;
                        default:
                            write = true;
                            break;
                    }
                }
                if(write)
                    writer.serializeAsField(pojo, jgen, provider);
            } else if (!jgen.canOmitFields()) { // since 2.3
                writer.serializeAsOmittedField(pojo, jgen, provider);
            }
        }

        @Override
        protected boolean include(BeanPropertyWriter writer) {
            return true;
        }

        @Override
        protected boolean include(PropertyWriter writer) {
            return true;
        }
    }
}
