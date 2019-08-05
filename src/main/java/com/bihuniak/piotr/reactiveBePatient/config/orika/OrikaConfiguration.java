package com.bihuniak.piotr.reactiveBePatient.config.orika;

import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

import static java.util.Collections.emptyList;

@Configuration
public class OrikaConfiguration {

    private final List<OrikaBeanMapping> mappings;

    @Autowired(required = false)
    public OrikaConfiguration() {
        this.mappings = emptyList();
    }

    @Autowired(required = false)
    public OrikaConfiguration(List<OrikaBeanMapping> mappings) {
        this.mappings = mappings;
    }

    @Bean
    public MapperFacade mapperFacade() {
        MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
        mappings.forEach(mapping -> mapping.configure(mapperFactory));
        return mapperFactory.getMapperFacade();
    }
}
