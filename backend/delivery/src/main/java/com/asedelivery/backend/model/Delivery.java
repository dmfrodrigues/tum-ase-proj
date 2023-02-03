package com.asedelivery.backend.model;

import java.io.Serializable;
import java.util.TreeSet;
import java.util.Date;
import java.util.Iterator;
import java.util.SortedSet;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.asedelivery.common.model.Role;
import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
    public Customer customer;

    @DocumentReference
    public Box box;

    @DocumentReference
    public Dispatcher createdBy;

    @DocumentReference
    public Deliverer deliverer;

    public String pickupAddress;

    public enum State {
        ORDERED,
        PICKED_UP,
        DELIVERED,
        COMPLETED;

        public State next(){
            switch(this){
                case ORDERED: return PICKED_UP;
                case PICKED_UP: return DELIVERED;
                case DELIVERED: return COMPLETED;
                default: throw new IllegalStateException("Delivery state cannot be advanced beyond COMPLETED");
            }
        }
    };

    static public class Event implements Comparable<Event>, Serializable {
        public Date date;
        public State state;

        public Event(Date date, State state){
            this.date = date;
            this.state = state;
        }

        @Override
        public int compareTo(Event e) {
            int ret = date.compareTo(e.date);
            if(ret != 0) return ret;
            else return state.compareTo(e.state);
        }
    };

    private TreeSet<Event> events;

    public Delivery(Customer customer, Dispatcher createdBy, Deliverer deliverer, String pickupAddress, Box box) {
        assert box.customer.equals(customer);

        this.customer = customer;
        this.createdBy = createdBy;
        this.deliverer = deliverer;
        this.pickupAddress = pickupAddress;
        this.box = box;

        events = new TreeSet<>();
    }

    public String getId() {
        return id;
    }

    @JsonIgnore
    public State getState(){
        return events.last().state;
    }

    @JsonIgnore
    public Date getStateDate(){
        return events.last().date;
    }

    public Date advanceState() throws IllegalStateException {
        Date now = new Date();
        if(now.before(getStateDate()))
            throw new IllegalStateException("Now comes before the most recent event; database is likely broken");

        State state = getState();
        State newState = state.next();

        events.add(new Event(now, newState));
        return now;
    }

    public TreeSet<Event> getEvents(){
        return events;
    }

    public void setEvents(SortedSet<Event> events) throws IllegalArgumentException {
        if(events.size() > 1){
            Iterator<Event> it1 = events.iterator(), it2 = events.iterator();
            it2.next();
            while(it2.hasNext()){
                Event e1 = it1.next(), e2 = it2.next();
                Date d1 = e1.date, d2 = e2.date;
                if(d1.after(d2))
                    throw new IllegalArgumentException("Dates are not sorted; odd. Please report to developers");
                State s1 = e1.state, s2 = e2.state;
                if(!s2.equals(s1.next()))
                    throw new IllegalArgumentException("States are not consecutive: got " + s1 + " and then " + s2);
            }
        }

        this.events = new TreeSet<Event>(events);
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
