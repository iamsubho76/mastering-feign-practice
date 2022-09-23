package com.arnoldgalovics.online.store.service.inventory.error.handler;

import com.arnoldgalovics.online.store.service.common.error.FeignHTTPExceptionHandler;
import com.arnoldgalovics.online.store.service.common.model.ErrorResponse;
import com.arnoldgalovics.online.store.service.inventory.error.ProductCreationFailedException;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Response;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Component
public class ProductCreationFailedExceptionHandler implements FeignHTTPExceptionHandler {
    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Exception handle(Response response) {
        if (response.status() == HttpStatus.BAD_REQUEST.value()) {
            try {
                String responseBody = new String(response.body().asInputStream().readAllBytes(), StandardCharsets.UTF_8);
                ErrorResponse errorResponse = objectMapper.readValue(responseBody, ErrorResponse.class);
                return new ProductCreationFailedException(errorResponse.getMessage());
            } catch (IOException e) {
                throw new RuntimeException("Error while deserializing the response body");
            }
        }
        return null;
    }
}
