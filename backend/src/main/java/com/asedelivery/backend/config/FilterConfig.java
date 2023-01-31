package com.asedelivery.backend.config;

import org.springframework.context.annotation.Configuration;

import com.asedelivery.backend.Models.Delivery;
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

        objectMapper.setFilterProvider(simpleFilterProvider);
    }
}
