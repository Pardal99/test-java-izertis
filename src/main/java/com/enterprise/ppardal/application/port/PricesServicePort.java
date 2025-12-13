package com.enterprise.ppardal.application.port;

import com.enterprise.ppardal.domain.model.Price;
import com.enterprise.ppardal.infrastructure.adapter.api.model.request.PriceRequest;

public interface PricesServicePort {

    Price findProductPriceByApplicationDate(PriceRequest request);

}
