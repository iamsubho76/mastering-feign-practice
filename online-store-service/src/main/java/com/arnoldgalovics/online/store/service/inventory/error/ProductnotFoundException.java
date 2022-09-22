package com.arnoldgalovics.online.store.service.inventory.error;

public class ProductnotFoundException extends RuntimeException {
    public ProductnotFoundException(String message) {
        super(message);
    }
}
