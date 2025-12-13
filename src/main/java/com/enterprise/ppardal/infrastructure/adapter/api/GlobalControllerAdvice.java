
package com.enterprise.ppardal.infrastructure.adapter.api;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.enterprise.ppardal.domain.exception.PriceNotFoundException;
import com.enterprise.ppardal.infrastructure.adapter.api.model.response.ErrorResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalControllerAdvice extends ResponseEntityExceptionHandler {

	@Override
	@Nullable
    public ResponseEntity<Object> handleHttpMessageNotReadable(
            HttpMessageNotReadableException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request) {

        return buildInvalidRequestResponse(ex);
    }

    @Override
	@Nullable
    public ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request) {

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

    private ResponseEntity<Object> buildInvalidRequestResponse(Exception ex) {
        log.error("Invalid request payload", ex);
        return ResponseEntity
                .badRequest()
                .body(new ErrorResponse(
                        "Request body is malformed or contains invalid field formats"));
    }

}
