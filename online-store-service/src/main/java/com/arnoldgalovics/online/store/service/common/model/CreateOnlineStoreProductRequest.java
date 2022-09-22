package com.arnoldgalovics.online.store.service.common.model;

import lombok.Data;

@Data
public class CreateOnlineStoreProductRequest {
    private String name;
    private int initialStock;
}
