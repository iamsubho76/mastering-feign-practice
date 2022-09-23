package com.arnoldgalovics.online.store.service.inventory;

import com.arnoldgalovics.online.store.service.common.helper.OffsetDateTimeToMillisFormatter;
import com.arnoldgalovics.online.store.service.common.model.BaseClient;
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

@FeignClient(name = "inventory-service", url = "http://localhost:8081")
public interface InventoryServiceClient extends BaseClient {
    @PostMapping("/products")
    CreateProductResponse createProduct(CreateProductRequest request);

    @PostMapping("/products/{productId}/buy?amount={amount}&boughtAt={boughtAt}")
    void buy(@PathVariable("productId") String productId,
             @RequestParam("amount") int amount,
             @RequestParam(value = "boughtAt") OffsetDateTime boughtAt);
}
