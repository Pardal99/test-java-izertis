package com.enterprise.ppardal.infrastructure.adapter.api.mapper;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import com.enterprise.ppardal.domain.model.Price;
import com.enterprise.ppardal.infrastructure.adapter.api.model.response.PriceResponse;

@Mapper(componentModel = "spring")
public interface PricesApiMapper {

    @Mapping(target = "startDate", source = "startDate", qualifiedByName = "mapDateTime")
    @Mapping(target = "endDate", source = "endDate", qualifiedByName = "mapDateTime")
    PriceResponse toPriceResponse(Price price);

    // Mapping method for LocalDateTime to OffsetDateTime using UTC offset
    @Named("mapDateTime")
    default OffsetDateTime mapDateTime(LocalDateTime value) {
        return value == null ? null : value.atOffset(ZoneOffset.UTC);
    }
}
