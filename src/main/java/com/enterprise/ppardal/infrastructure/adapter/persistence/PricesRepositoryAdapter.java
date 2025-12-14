package com.enterprise.ppardal.infrastructure.adapter.persistence;

import java.time.LocalDateTime;
import java.util.List;

import com.enterprise.ppardal.application.port.PricesPersistencePort;
import com.enterprise.ppardal.domain.model.Price;
import com.enterprise.ppardal.infrastructure.adapter.persistence.mapper.PricesPersistenceMapper;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class PricesRepositoryAdapter implements PricesPersistencePort {

    private final PricesRepository pricesRepository;

    private final PricesPersistenceMapper mapper;

    @Override
    public List<Price> findTop2ByBrandIdAndProductIdAndApplicationDate(Long brandId, Long productId,
            LocalDateTime applicationDateUtc) {
        return mapper.toPriceList(pricesRepository.findTop2ByBrandIdAndProductIdAndApplicationDate(brandId, productId,
                applicationDateUtc));
    }

}
