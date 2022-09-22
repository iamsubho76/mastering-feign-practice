package com.arnoldgalovics.online.store.service.inventory.helper;

import com.arnoldgalovics.online.store.service.inventory.error.ProductCreationFailedException;
import com.arnoldgalovics.online.store.service.inventory.error.ProductnotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class InventoryServiceErrorDecoder implements ErrorDecoder {
    private ObjectMapper objectMapper = new ObjectMapper();
    private ErrorDecoder.Default defaultDecoder = new Default();

    @Override
    public Exception decode(String methodKey, Response response) {
        if ("InventoryServiceClient#createProduct(CreateProductRequest)".equals(methodKey)) {
            if (response.status() == HttpStatus.BAD_REQUEST.value()) {
                try {
                    String responseBody = new String(response.body().asInputStream().readAllBytes(), StandardCharsets.UTF_8);
                    ErrorResponse errorResponse = objectMapper.readValue(responseBody, ErrorResponse.class);
                    return new ProductCreationFailedException(errorResponse.getMessage());
                } catch (IOException e) {
                    throw new RuntimeException("Error while deserializing the response body", e);
                }
            }
        } else if("InventoryServiceClient#buy(String,int,OffsetDateTime)".equals(methodKey)) {
            if(response.status() == HttpStatus.NOT_FOUND.value()){
                try {
                    String responseBody = new String(response.body().asInputStream().readAllBytes(), StandardCharsets.UTF_8);
                    ErrorResponse errorResponse = objectMapper.readValue(responseBody, ErrorResponse.class);
                    return new ProductnotFoundException(errorResponse.getMessage());
                } catch (IOException e) {
                    throw new RuntimeException("Error while deserializing the response body", e);
                }
            }
        }
        return defaultDecoder.decode(methodKey, response);
    }

    @Data
    static class ErrorResponse {
        private String message;
    }
}
