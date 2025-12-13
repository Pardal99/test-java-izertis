package com.enterprise.ppardal.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.cfg.CoercionAction;
import com.fasterxml.jackson.databind.cfg.CoercionInputShape;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.databind.type.LogicalType;

@Configuration
public class JacksonConfig {

    @Bean
    @Primary
    public ObjectMapper objectMapper() {
        // ObjectMapper configuration to enforce strict deserialization rules
        ObjectMapper mapper = JsonMapper.builder()
                // Use SNAKE_CASE for JSON properties
                .propertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE)
                // Exclude null values from serialization
                .serializationInclusion(JsonInclude.Include.NON_NULL)
                // Disable coercion of scalars to prevent implicit type conversions
                .disable(MapperFeature.ALLOW_COERCION_OF_SCALARS)
                // Fail on unknown properties during deserialization
                .enable(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES)
                .build();

        // Prevent coercion from float to integer types. Avoids accepting decimals where
        // integers are expected.
        mapper.coercionConfigFor(LogicalType.Integer)
                .setCoercion(CoercionInputShape.Float, CoercionAction.Fail);

        // Register modules for Java 8 date/time types and other features
        mapper.findAndRegisterModules();
        return mapper;
    }

}