package com.example.orderservice.producer;

import com.example.orderservice.entity.OrderEntity;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderMessage {

        private int qty;
        private String productId;

        public static OrderMessage of(OrderEntity orderEntity) {
                return OrderMessage.builder()
                        .qty(orderEntity.getQty())
                        .productId(orderEntity.getProductId())
                        .build();
        }
}
