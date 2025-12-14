package com.enterprise.ppardal.infrastructure.adapter.persistence.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.enterprise.ppardal.domain.model.Price;
import com.enterprise.ppardal.infrastructure.adapter.persistence.model.PriceEntity;

@SpringBootTest
class PricesPersistenceMapperTest {

    @Autowired
    private PricesPersistenceMapper mapper;

    @Test
    void shouldMapPriceToPriceEntity() {
            Price price = Price.builder()
                    .id(1L)
                    .brandId(1L)
                    .productId(35455L)
                    .priceList(1)
                    .startDate(LocalDateTime.of(2020, 6, 14, 0, 0))
                    .endDate(LocalDateTime.of(2020, 12, 31, 23, 59, 59))
                    .price(BigDecimal.valueOf(35.50))
                    .currency("EUR")
                    .priority(0)
                    .build();

        PriceEntity entity = mapper.toPriceEntity(price);

        assertNotNull(entity);
        assertEquals(price.getId(), entity.getId());
        assertEquals(price.getBrandId(), entity.getBrandId());
        assertEquals(price.getProductId(), entity.getProductId());
        assertEquals(price.getPriceList(), entity.getPriceList());
        assertEquals(price.getStartDate(), entity.getStartDate());
        assertEquals(price.getEndDate(), entity.getEndDate());
        assertEquals(price.getPrice(), entity.getPrice());
        assertEquals(price.getCurrency(), entity.getCurrency());
        assertEquals(price.getPriority(), entity.getPriority());
    }

    @Test
    void shouldReturnNullWhenPriceIsNull() {
        PriceEntity entity = mapper.toPriceEntity(null);
        assertNull(entity);
    }

    @Test
    void shouldMapPriceEntityToPrice() {
        PriceEntity entity = new PriceEntity(
                2L,
                1L,
                LocalDateTime.of(2020, 6, 15, 0, 0),
                LocalDateTime.of(2020, 6, 15, 11, 0),
                35455L,
                3,
                BigDecimal.valueOf(30.50),
                "EUR",
                1);

        Price price = mapper.toPrice(entity);

        assertNotNull(price);
        assertEquals(entity.getId(), price.getId());
        assertEquals(entity.getBrandId(), price.getBrandId());
        assertEquals(entity.getProductId(), price.getProductId());
        assertEquals(entity.getPriceList(), price.getPriceList());
        assertEquals(entity.getStartDate(), price.getStartDate());
        assertEquals(entity.getEndDate(), price.getEndDate());
        assertEquals(entity.getPrice(), price.getPrice());
        assertEquals(entity.getCurrency(), price.getCurrency());
        assertEquals(entity.getPriority(), price.getPriority());
    }

    @Test
    void shouldReturnNullWhenPriceEntityIsNull() {
        Price price = mapper.toPrice(null);
        assertNull(price);
    }

    @Test
    void shouldMapPriceEntityListToPriceList() {
        PriceEntity entity1 = new PriceEntity(
                1L,
                1L,
                LocalDateTime.of(2020, 6, 14, 0, 0),
                LocalDateTime.of(2020, 12, 31, 23, 59, 59),
                35455L,
                1,
                BigDecimal.valueOf(35.50),
                "EUR",
                0);

        PriceEntity entity2 = new PriceEntity(
                2L,
                1L,
                LocalDateTime.of(2020, 6, 15, 16, 0),
                LocalDateTime.of(2020, 12, 31, 23, 59, 59),
                35455L,
                4,
                BigDecimal.valueOf(38.95),
                "EUR",
                1);

        List<Price> prices = mapper.toPriceList(List.of(entity1, entity2));

        assertNotNull(prices);
        assertEquals(2, prices.size());
        assertEquals(entity1.getId(), prices.get(0).getId());
        assertEquals(entity2.getId(), prices.get(1).getId());
    }

    @Test
    void shouldReturnEmptyListWhenPriceEntityListIsNull() {
        List<Price> prices = mapper.toPriceList(null);

        assertNotNull(prices);
        assertTrue(prices.isEmpty());
    }
}
