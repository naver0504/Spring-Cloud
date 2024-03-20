package com.example.catalogservice.dto;

import lombok.Data;

@Data
public class CatalogDto {

    private String productId;
    private int qty;
    private int unitPrice;
    private int totalPrice;
    private String orderId;
    private String userId;
}
