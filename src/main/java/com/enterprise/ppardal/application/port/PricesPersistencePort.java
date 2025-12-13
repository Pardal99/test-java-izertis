package com.enterprise.ppardal.application.port;

import java.time.LocalDateTime;
import java.util.List;

import com.enterprise.ppardal.domain.model.Price;

public interface PricesPersistencePort {

    public List<Price> findTop2ByBrandIdAndProductIdAndApplicationDate(
        Long brandId,
        Long productId,
        LocalDateTime applicationDateUtc
    );

}
