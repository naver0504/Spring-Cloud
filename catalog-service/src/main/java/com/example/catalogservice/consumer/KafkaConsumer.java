package com.example.catalogservice.consumer;

import com.example.catalogservice.repository.CatalogRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class KafkaConsumer {

    private final CatalogRepository catalogRepository;

    @Transactional
    @KafkaListener(topics = KafkaConstant.TOPIC, groupId = KafkaConstant.GROUP_ID)
    public void updateQty(StockUpdateMessage catalogMessage) {

        log.info("Catalog Message: {}", catalogMessage);

        catalogRepository.findByProductId(catalogMessage.getProductId())
                    .ifPresentOrElse(catalogEntity ->
                                    catalogEntity.setStock(catalogEntity.getStock() - catalogMessage.getQty())
                    , () -> log.error("Product not found: {}", catalogMessage.getProductId()));
    }

}
