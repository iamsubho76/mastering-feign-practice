package com.arnoldgalovics.online.store.service.inventory.error;

public class ProductCreationFailedException extends RuntimeException {
    public ProductCreationFailedException(String message) {
        super(message);
    }
}
