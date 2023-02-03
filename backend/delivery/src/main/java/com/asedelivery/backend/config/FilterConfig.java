package com.asedelivery.backend.config;

import org.springframework.context.annotation.Configuration;

import com.asedelivery.backend.model.Box;
import com.asedelivery.backend.model.Delivery;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

@Configuration
public class FilterConfig {
    public FilterConfig (ObjectMapper objectMapper) {
        SimpleFilterProvider simpleFilterProvider = new SimpleFilterProvider().setFailOnUnknownId(true);
        
        simpleFilterProvider.addFilter(
            "deliveryPropertyFilter",
            new Delivery.PropertyFilter()
        );

        simpleFilterProvider.addFilter(
            "boxPropertyFilter",
            new Box.PropertyFilter()
        );

        objectMapper.setFilterProvider(simpleFilterProvider);
    }
}
