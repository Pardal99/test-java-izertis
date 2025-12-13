package com.enterprise.ppardal.infrastructure.adapter.persistence.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.enterprise.ppardal.domain.model.Price;
import com.enterprise.ppardal.infrastructure.adapter.persistence.model.PriceEntity;

@Mapper(componentModel = "spring")
public interface PricesPersistenceMapper {

    PriceEntity toPriceEntity(Price price);

    Price toPrice(PriceEntity priceEntity);

    List<Price> toPriceList(List<PriceEntity> priceEntities);

}
