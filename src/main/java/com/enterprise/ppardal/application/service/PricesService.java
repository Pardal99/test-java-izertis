package com.enterprise.ppardal.application.service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Comparator;
import java.util.List;

import com.enterprise.ppardal.application.port.PricesPersistencePort;
import com.enterprise.ppardal.application.port.PricesServicePort;
import com.enterprise.ppardal.domain.exception.PriceNotFoundException;
import com.enterprise.ppardal.domain.model.Price;
import com.enterprise.ppardal.infrastructure.adapter.api.model.request.PriceRequest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class PricesService implements PricesServicePort {

    private final PricesPersistencePort pricesPersistencePort;

    @Override
    public Price findProductPriceByApplicationDate(PriceRequest request) {
        log.info("Finding product price for request: {}", request);

        // Convert application date to UTC LocalDateTime to avoid timezone issues
        log.trace("Converting application date {} to UTC LocalDateTime", request.getApplicationDate());
        LocalDateTime applicationDate = request.getApplicationDate()
                .withOffsetSameInstant(ZoneOffset.UTC)
                .toLocalDateTime();
        log.trace("Converted application date: {}", applicationDate);

        List<Price> prices = pricesPersistencePort.findTop2ByBrandIdAndProductIdAndApplicationDate(
                request.getBrandId(),
                request.getProductId(),
                applicationDate);

        Price price = selectPriceByPriority(prices);
        log.info("Price found: {}", price);
        return price;
    }

    /**
     * Selects the price to apply from a list, following business rules:
     * - If the list is empty, throws exception.
     * - Returns the price with the highest priority.
     */
    private Price selectPriceByPriority(List<Price> prices) {
        if (prices == null || prices.isEmpty()) {
            log.info("No prices found for the given values.");
            throw new PriceNotFoundException("No price found for the given parameters.");
        }

        return prices.stream()
                .max(Comparator.comparingInt(Price::getPriority))
                .orElseThrow(() -> new PriceNotFoundException("No price found for the given parameters."));
    }

}
