package com.example.catalogservice.consumer;

import lombok.Data;

@Data
public class StockUpdateMessage {

    private int qty;
    private String productId;
}
