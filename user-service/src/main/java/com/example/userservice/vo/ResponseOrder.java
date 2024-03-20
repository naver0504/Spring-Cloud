package com.example.userservice.vo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ResponseOrder {

    private String productId;
    private int qty;
    private int unitPrice;
    private int totalPrice;
    private LocalDateTime createdAt;

    private String orderId;
}
