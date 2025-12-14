package com.enterprise.ppardal.shared.config;

import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@Configuration
@OpenAPIDefinition(info = @Info(title = "Pricing Service API", version = "1.0", description = "API documentation for the Pricing Service"))
public class OpenApiConfig {

}
