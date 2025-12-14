package com.enterprise.ppardal.infrastructure.adapter.api.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.enterprise.ppardal.domain.model.Price;
import com.enterprise.ppardal.infrastructure.adapter.api.model.response.PriceResponse;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class PricesApiMapper {

    private final ModelMapper modelMapper;

    public PriceResponse toPriceResponse(Price price) {
        if (price == null) {
            return null;
        }
        return modelMapper.map(price, PriceResponse.class);
    }
    
}
