package com.enterprise.ppardal.infrastructure.adapter.persistence;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import com.enterprise.ppardal.domain.model.Price;
import com.enterprise.ppardal.infrastructure.adapter.persistence.mapper.PricesPersistenceMapperImpl;

@DataJpaTest
@Import({PricesRepositoryAdapter.class, PricesPersistenceMapperImpl.class})
class PricesRepositoryAdapterTest {

    @Autowired
    private PricesRepositoryAdapter adapter;

    @Test
    void shouldReturnPricesFromDatabaseMappedToDomain() {
        List<Price> prices = adapter.findTop2ByBrandIdAndProductIdAndApplicationDate(
                1L,
                35455L,
                LocalDateTime.of(2020, 6, 14, 10, 0)
        );

        assertNotNull(prices);
        assertEquals(1, prices.size());
        assertEquals(new BigDecimal("35.50"), prices.get(0).getPrice());
    }
}
