package com.arnoldgalovics.inventory.service.error;

public class ProductnotFoundException extends RuntimeException {
    public ProductnotFoundException(String message) {
        super(message);
    }
}
