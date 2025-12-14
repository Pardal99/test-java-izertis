package com.enterprise.ppardal.infrastructure.adapter.api.model.response;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class PriceResponse {

    @JsonProperty("product_id")
    private Long productId;

    @JsonProperty("brand_id")
    private Long brandId;

    @JsonProperty("price_list")
    private Integer priceList;

    // The offset (timezone) will be included in the serialized response to avoid
    // ambiguity
    @JsonProperty("start_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private OffsetDateTime startDate;

    @JsonProperty("end_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private OffsetDateTime endDate;

    @JsonProperty("price")
    private BigDecimal price;

}
