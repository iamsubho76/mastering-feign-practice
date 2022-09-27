package com.arnoldgalovics.online.store.service.inventory;

import com.arnoldgalovics.online.store.service.common.error.HandleFeignException;
import com.arnoldgalovics.online.store.service.common.helper.OffsetDateTimeToMillisFormatter;
import com.arnoldgalovics.online.store.service.common.model.BaseClient;
import com.arnoldgalovics.online.store.service.inventory.error.handler.ProductCreationFailedExceptionHandler;
import com.arnoldgalovics.online.store.service.inventory.error.handler.ProductNotFoundExceptionHandler;
import com.arnoldgalovics.online.store.service.inventory.model.CreateProductRequest;
import com.arnoldgalovics.online.store.service.inventory.model.CreateProductResponse;
import feign.Headers;
import feign.Param;
import feign.RequestLine;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.OffsetDateTime;

//The URL is not required as the logical name 'inventory-service' is registered in Eureka which is sufficient to find the service
@FeignClient(name = "inventory-service")
public interface InventoryServiceClient extends BaseClient {
    @PostMapping("/products")
    @HandleFeignException(ProductCreationFailedExceptionHandler.class)
    CreateProductResponse createProduct(CreateProductRequest request);

    @PostMapping("/products/{productId}/buy?amount={amount}&boughtAt={boughtAt}")
    @HandleFeignException(ProductNotFoundExceptionHandler.class)
    void buy(@PathVariable("productId") String productId,
             @RequestParam("amount") int amount,
             @RequestParam(value = "boughtAt") OffsetDateTime boughtAt);
}
