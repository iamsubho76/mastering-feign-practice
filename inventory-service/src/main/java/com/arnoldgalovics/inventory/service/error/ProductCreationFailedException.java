package com.arnoldgalovics.inventory.service.error;

public class ProductCreationFailedException extends RuntimeException {
    public ProductCreationFailedException(String message) {
        super(message);
    }
}
