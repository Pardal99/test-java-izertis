package com.enterprise.ppardal.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.enterprise.ppardal.application.port.PricesPersistencePort;
import com.enterprise.ppardal.infrastructure.adapter.persistence.PricesRepository;
import com.enterprise.ppardal.infrastructure.adapter.persistence.PricesRepositoryAdapter;
import com.enterprise.ppardal.infrastructure.adapter.persistence.mapper.PricesPersistenceMapper;

@Configuration
public class InfrastructureConfig {

    @Bean
    PricesPersistencePort pricesPersistencePort(PricesRepository pricesRepository,
            PricesPersistenceMapper mapper) {
        return new PricesRepositoryAdapter(pricesRepository, mapper);
    }
    
}