package com.enterprise.ppardal.infrastructure.adapter.api;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.enterprise.ppardal.application.port.PricesServicePort;
import com.enterprise.ppardal.infrastructure.adapter.api.mapper.PricesApiMapper;
import com.enterprise.ppardal.infrastructure.adapter.api.model.request.PriceRequest;
import com.enterprise.ppardal.infrastructure.adapter.api.model.response.PriceResponse;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/v1/prices")
@AllArgsConstructor
@Validated
public class PricesControllerAdapter {

	private final PricesServicePort pricesServicePort;

	private final PricesApiMapper mapper;

	@PostMapping("/find-by-application-date")
	public ResponseEntity<PriceResponse> findProductPriceByApplicationDate(@Valid @RequestBody PriceRequest request) {
		return ResponseEntity.ok(mapper.toPriceResponse(
				pricesServicePort.findProductPriceByApplicationDate(request)));
	}

}
