package com.arnoldgalovics.inventory.service.dto;

import lombok.Data;

@Data
public class CreateProductRequest {
    private String name;
    private int initialStock;
}
