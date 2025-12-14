package com.enterprise.ppardal.infrastructure.adapter.persistence.mapper;

import java.util.Collections;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.enterprise.ppardal.domain.model.Price;
import com.enterprise.ppardal.infrastructure.adapter.persistence.model.PriceEntity;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class PricesPersistenceMapper {

    private final ModelMapper modelMapper;

    public PriceEntity toPriceEntity(Price price) {
        return price == null ? null : modelMapper.map(price, PriceEntity.class);
    }

    public Price toPrice(PriceEntity entity) {
        return entity == null ? null : modelMapper.map(entity, Price.class);
    }

    public List<Price> toPriceList(List<PriceEntity> entities) {
        if (entities == null) {
            return Collections.emptyList();
        }
        return entities.stream()
                .map(this::toPrice)
                .toList();
    }
    
}