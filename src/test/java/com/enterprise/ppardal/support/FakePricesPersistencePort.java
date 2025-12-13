package com.enterprise.ppardal.support;

import java.time.LocalDateTime;
import java.util.List;

import com.enterprise.ppardal.application.port.PricesPersistencePort;
import com.enterprise.ppardal.domain.model.Price;

/**
 * Fake implementation of PricesPersistencePort for unit testing.
 * Allows deterministic control of returned data without using mocks.
 */
public class FakePricesPersistencePort implements PricesPersistencePort {

    private List<Price> pricesToReturn;

    public void setPricesToReturn(List<Price> pricesToReturn) {
        this.pricesToReturn = pricesToReturn;
    }

    @Override
    public List<Price> findTop2ByBrandIdAndProductIdAndApplicationDate(
            Long brandId,
            Long productId,
            LocalDateTime applicationDateUtc) {

        return pricesToReturn;
    }
}