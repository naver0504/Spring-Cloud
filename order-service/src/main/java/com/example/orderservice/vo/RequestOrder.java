package com.example.orderservice.vo;

import lombok.Data;

@Data
public class RequestOrder {

    private String productId;
    private int qty;
    private int unitPrice;

}
