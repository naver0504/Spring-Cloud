package com.example.catalogservice.consumer;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@EnableKafka
@Configuration
public class ConsumerKafkaConfig {

    @Bean
    ConcurrentKafkaListenerContainerFactory<String, StockUpdateMessage> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, StockUpdateMessage> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }

    @Bean
    public ConsumerFactory<String, StockUpdateMessage> consumerFactory() {



        Map<String, Object> config = new HashMap<>();
        /*
        The TYPE_MAPPINGS property is used to map the type of the message to the class that should be used to deserialize it.
        매우 중요 안하면 JSON 매핑이 안됌
        Producer Config에서도 OrderMesssage:해당프로젝트.OrderMessage 이런식으로 매핑해줘야함
        OrderMessage란 Producer에서 생성하는 메세지 Class 타입
        StockUpdateMessage란 Consumer에서 받는 메세지 Class 타입
        같은 프로젝트에서는 안 해도 되지만 다른 프로젝트에서는 해줘야함
        안할거면 String으로 발급하고 소비도 String으로 받아서 직접 매핑해줘야함
         */
        config.put(JsonDeserializer.TYPE_MAPPINGS, "OrderMessage:com.example.catalogservice.consumer.StockUpdateMessage");
        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, KafkaConstant.BOOTSTRAP_SERVERS_CONFIG);
        config.put(ConsumerConfig.GROUP_ID_CONFIG, KafkaConstant.GROUP_ID);
        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        config.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
        config.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, KafkaConstant.AUTO_OFFSET_RESET_CONFIG);
        return new DefaultKafkaConsumerFactory<>(config);
    }
}
