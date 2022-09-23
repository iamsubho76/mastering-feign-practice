package com.arnoldgalovics.inventory.service.handler;

import com.arnoldgalovics.inventory.service.error.ProductNotFoundException;
import com.arnoldgalovics.inventory.service.model.ErrorResponse;
import com.arnoldgalovics.inventory.service.error.ProductCreationFailedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ApiExceptionHandler {
    @ExceptionHandler({ProductCreationFailedException.class})
    public ResponseEntity<ErrorResponse> handleProductCreationFailure(ProductCreationFailedException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(e.getMessage()));
    }

    @ExceptionHandler({ProductNotFoundException.class})
    public ResponseEntity<ErrorResponse> handleProductBuyFailure(ProductNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse(e.getMessage()));
    }
}
