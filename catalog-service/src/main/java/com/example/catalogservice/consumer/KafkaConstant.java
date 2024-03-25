package com.example.catalogservice.consumer;

public class
KafkaConstant {
    public static final String BOOTSTRAP_SERVERS_CONFIG = "localhost:9092";
    public static final String GROUP_ID = "catalog-service";
    public static final String TOPIC = "example-catalog-topic";
    public static final String AUTO_OFFSET_RESET_CONFIG = "latest";
}
