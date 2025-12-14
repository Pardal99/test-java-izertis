package com.enterprise.ppardal.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Price {

    private Long id;

    private Long brandId;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private Long productId;

    private Integer priceList;

    private BigDecimal price;

    private String currency;

    private Integer priority;

}
