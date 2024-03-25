package com.example.orderservice.service;

import com.example.orderservice.dto.OrderDto;
import com.example.orderservice.entity.OrderEntity;
import com.example.orderservice.producer.OrderMessage;
import com.example.orderservice.producer.KafkaConstant;
import com.example.orderservice.repository.OrderRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderService{

    private final ModelMapper modelMapper;
    private final OrderRepository orderRepository;
    private final KafkaTemplate kafkaTemplate;



    @Override
    @Transactional
    public OrderDto createOrder(OrderDto orderDetails) {
        orderDetails.setOrderId(UUID.randomUUID().toString());
        orderDetails.setTotalPrice(orderDetails.getQty() * orderDetails.getUnitPrice());

        final OrderEntity orderEntity = modelMapper.map(orderDetails, OrderEntity.class);
        orderRepository.save(orderEntity);

        log.info("Catalog Message: {}", OrderMessage.of(orderEntity));
        kafkaTemplate.send(KafkaConstant.TOPIC, OrderMessage.of(orderEntity));

        return modelMapper.map(orderEntity, OrderDto.class);
    }

    @Override
    public OrderDto getOrderByOrderId(String orderId) {
        final OrderEntity orderEntity = orderRepository.findByOrderId(orderId).orElseThrow(() -> new RuntimeException("Not Found Order"));
        return modelMapper.map(orderEntity, OrderDto.class);
    }

    @Override
    public List<OrderEntity> getOrdersByUserId(String userId) {
        return orderRepository.findByUserId(userId);
    }
}
