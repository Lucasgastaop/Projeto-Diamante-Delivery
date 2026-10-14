package com.delivery.order.web;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.RestClientException;

import com.delivery.order.error.BadRequestException;
import com.delivery.order.error.ConflictException;
import com.delivery.order.error.NotFoundException;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(BadRequestException.class)
    ResponseEntity<ApiError> badRequest(BadRequestException exception) {
        return ResponseEntity.badRequest().body(new ApiError(exception.getMessage()));
    }

    @ExceptionHandler(NotFoundException.class)
    ResponseEntity<ApiError> notFound(NotFoundException exception) {
        return ResponseEntity.status(404).body(new ApiError(exception.getMessage()));
    }

    @ExceptionHandler(ConflictException.class)
    ResponseEntity<ApiError> conflict(ConflictException exception) {
        return ResponseEntity.status(409).body(new ApiError(exception.getMessage()));
    }

    @ExceptionHandler(RestClientException.class)
    ResponseEntity<ApiError> paymentUnavailable(RestClientException exception) {
        return ResponseEntity.status(502).body(new ApiError("Payment service unavailable"));
    }
}
