package com.asedelivery.backend.model;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.asedelivery.common.model.Role;
import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.BeanPropertyWriter;
import com.fasterxml.jackson.databind.ser.PropertyWriter;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;

@Document("agent")
@JsonFilter("boxPropertyFilter")
public class Box extends Agent {
    public String address;

    @DocumentReference
    public Customer customer;

    public Box(String username, String address) {
        super(username);
        this.address = address;
    }

    public Role getRole() {
        return Role.BOX;
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
                            write =
                                authority.equals("ROLE_" + Role.DISPATCHER_STR) ||
                                authority.equals("ROLE_" + Role.BOX_STR);
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
