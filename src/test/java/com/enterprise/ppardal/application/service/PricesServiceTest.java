package com.enterprise.ppardal.application.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.enterprise.ppardal.application.port.PricesServicePort;
import com.enterprise.ppardal.domain.exception.PriceNotFoundException;
import com.enterprise.ppardal.domain.model.Price;
import com.enterprise.ppardal.infrastructure.adapter.api.model.request.PriceRequest;
import com.enterprise.ppardal.support.FakePricesPersistencePort;

class PricesServiceTest {

    private PricesServicePort pricesService;
    private FakePricesPersistencePort fakePersistencePort;

    @BeforeEach
    void setUp() {
        fakePersistencePort = new FakePricesPersistencePort();
        pricesService = new PricesService(fakePersistencePort);
    }

    @Test
    void test1_shouldReturnPriceWhenSingleResultExists() {
        PriceRequest request = buildRequest();
        Price price = buildPrice(0);

        fakePersistencePort.setPricesToReturn(List.of(price));

        Price result = pricesService.findProductPriceByApplicationDate(request);

        assertEquals(price, result);
    }

    @Test
    void test2_shouldReturnPriceWithHighestPriorityWhenMultipleResultsExist() {
        PriceRequest request = buildRequest();

        Price lowPriority = buildPrice(0);
        Price highPriority = buildPrice(1);

        fakePersistencePort.setPricesToReturn(List.of(lowPriority, highPriority));

        Price result = pricesService.findProductPriceByApplicationDate(request);

        assertEquals(highPriority, result);
    }

    @Test
    void test3_shouldThrowExceptionWhenNoPricesFound() {
        PriceRequest request = buildRequest();

        fakePersistencePort.setPricesToReturn(List.of());

        assertThrows(
                PriceNotFoundException.class,
                () -> pricesService.findProductPriceByApplicationDate(request));
    }

    @Test
    void test4_shouldThrowExceptionWhenPricesIsNull() {
        PriceRequest request = buildRequest();

        fakePersistencePort.setPricesToReturn(null);

        assertThrows(
                PriceNotFoundException.class,
                () -> pricesService.findProductPriceByApplicationDate(request));
    }

    // ========== Helpers ==========

    private PriceRequest buildRequest() {
        PriceRequest request = new PriceRequest();
        request.setBrandId(1L);
        request.setProductId(35455L);
        request.setApplicationDate(
                OffsetDateTime.of(
                        2020,
                        6,
                        14,
                        10,
                        0,
                        0,
                        0,
                        ZoneOffset.ofHours(2)));
        return request;
    }

    private Price buildPrice(int priority) {
        return Price.builder()
                .priority(priority)
                .build();
    }
}