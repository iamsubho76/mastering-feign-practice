package com.arnoldgalovics.online.store.service.common.model;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class CreateOnlineStoreProductResponse {
    private UUID productId;
    private String name;
    private int stock;
}
