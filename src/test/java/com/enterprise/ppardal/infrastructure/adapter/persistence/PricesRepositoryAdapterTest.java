package com.enterprise.ppardal.infrastructure.adapter.persistence;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.enterprise.ppardal.domain.model.Price;
import com.enterprise.ppardal.infrastructure.adapter.persistence.mapper.PricesPersistenceMapper;
import com.enterprise.ppardal.infrastructure.adapter.persistence.model.PriceEntity;

@ExtendWith(MockitoExtension.class)
class PricesRepositoryAdapterTest {

    @Mock
    private PricesRepository pricesRepository;

    @Mock
    private PricesPersistenceMapper mapper;

    @InjectMocks
    private PricesRepositoryAdapter adapter;

    @Test
    void shouldReturnMappedPricesFromRepository() {
        Long brandId = 1L;
        Long productId = 35455L;
        LocalDateTime applicationDate = LocalDateTime.of(2020, 6, 14, 10, 0);

        PriceEntity priceEntity = new PriceEntity();
        priceEntity.setId(1L);
        priceEntity.setBrandId(1L);
        priceEntity.setProductId(35455L);
        priceEntity.setPriceList(1);
        priceEntity.setStartDate(LocalDateTime.of(2020, 6, 14, 0, 0));
        priceEntity.setEndDate(LocalDateTime.of(2020, 12, 31, 23, 59, 59));
        priceEntity.setPrice(BigDecimal.valueOf(35.50));
        priceEntity.setCurrency("EUR");
        priceEntity.setPriority(0);

        List<PriceEntity> entities = List.of(priceEntity);

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

        List<Price> domainPrices = List.of(price);

        when(pricesRepository
                .findTop2ByBrandIdAndProductIdAndApplicationDate(
                        brandId, productId, applicationDate))
                .thenReturn(entities);

        when(mapper.toPriceList(entities))
                .thenReturn(domainPrices);

        List<Price> result = adapter.findTop2ByBrandIdAndProductIdAndApplicationDate(
                brandId, productId, applicationDate);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(price, result.get(0));

        verify(pricesRepository)
                .findTop2ByBrandIdAndProductIdAndApplicationDate(
                        brandId, productId, applicationDate);

        verify(mapper).toPriceList(entities);
        verifyNoMoreInteractions(pricesRepository, mapper);
    }

}
