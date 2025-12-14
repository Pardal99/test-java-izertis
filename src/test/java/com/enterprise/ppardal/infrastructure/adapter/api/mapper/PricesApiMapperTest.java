package com.enterprise.ppardal.infrastructure.adapter.api.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.enterprise.ppardal.domain.model.Price;
import com.enterprise.ppardal.infrastructure.adapter.api.model.response.PriceResponse;

@SpringBootTest
class PricesApiMapperTest {

    @Autowired
    private PricesApiMapper mapper;

    @Test
    void shouldMapAllFieldsCorrectly() {
        LocalDateTime start = LocalDateTime.of(2020, 6, 14, 0, 0);
        LocalDateTime end = LocalDateTime.of(2020, 12, 31, 23, 59, 59);

        Price price = Price.builder()
                .brandId(1L)
                .productId(35455L)
                .priceList(1)
                .startDate(start)
                .endDate(end)
                .price(new BigDecimal("35.50"))
                .currency("EUR")
                .priority(0)
                .build();

        PriceResponse response = mapper.toPriceResponse(price);

        assertNotNull(response);
        assertEquals(1L, response.getBrandId());
        assertEquals(35455L, response.getProductId());
        assertEquals(1, response.getPriceList());
        assertEquals(new BigDecimal("35.50"), response.getPrice());
        assertEquals(start.atOffset(ZoneOffset.UTC), response.getStartDate());
        assertEquals(end.atOffset(ZoneOffset.UTC), response.getEndDate());
    }

    @Test
    void shouldReturnNullWhenSourceIsNull() {
        PriceResponse response = mapper.toPriceResponse(null);
        assertNull(response);
    }
    
}
