package com.enterprise.ppardal.application.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.enterprise.ppardal.application.port.PricesPersistencePort;
import com.enterprise.ppardal.application.port.PricesServicePort;
import com.enterprise.ppardal.application.service.PricesService;

@Configuration
public class ApplicationConfig {

    @Bean
    PricesServicePort pricesServicePort(PricesPersistencePort pricesPersistencePort) {
        return new PricesService(pricesPersistencePort);
    }

}
