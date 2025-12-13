
package com.enterprise.ppardal.infrastructure.adapter.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.enterprise.ppardal.domain.exception.PriceNotFoundException;
import com.enterprise.ppardal.infrastructure.adapter.api.model.response.ErrorResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalControllerAdvice {

	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<ErrorResponse> handleInvalidRequestPayload(HttpMessageNotReadableException ex) {
		return buildInvalidRequestResponse(ex);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorResponse> handleInvalidRequestPayload(MethodArgumentNotValidException ex) {
		return buildInvalidRequestResponse(ex);
	}

	@ExceptionHandler(PriceNotFoundException.class)
	public ResponseEntity<ErrorResponse> handlePriceNotFoundException(PriceNotFoundException ex) {
		return new ResponseEntity<>(new ErrorResponse("No price found for the given parameters."),
				HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> handleGenericException(Exception ex) {
		log.error("Unexpected error: ", ex);
		return new ResponseEntity<>(new ErrorResponse("Internal server error"), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	private ResponseEntity<ErrorResponse> buildInvalidRequestResponse(Exception ex) {
		log.error("Invalid request payload", ex);
		return new ResponseEntity<>(new ErrorResponse(
				"Request body is malformed or contains invalid field formats"), HttpStatus.BAD_REQUEST);
	}

}
