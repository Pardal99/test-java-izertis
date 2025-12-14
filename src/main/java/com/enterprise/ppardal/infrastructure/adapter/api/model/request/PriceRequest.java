package com.enterprise.ppardal.infrastructure.adapter.api.model.request;

import java.time.OffsetDateTime;

import com.enterprise.ppardal.shared.config.deserializer.StrictOffsetDateTimeDeserializer;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PriceRequest {

    @NotNull(message = "brandId must not be null")
    @JsonProperty("brand_id")
    private Long brandId;

    @NotNull(message = "productId must not be null")
    @JsonProperty("product_id")
    private Long productId;

    /**
     * ISO-8601 format with timezone, e.g., "2020-06-14T10:00:00+00:00"
     * Mandatory to specify timezone to avoid ambiguity
     */
    @NotNull(message = "applicationDate must not be null")
    @JsonProperty("application_date")
    @JsonDeserialize(using = StrictOffsetDateTimeDeserializer.class)
    private OffsetDateTime applicationDate;
    
}
