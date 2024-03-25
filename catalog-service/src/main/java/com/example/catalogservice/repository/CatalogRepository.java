package com.example.catalogservice.repository;

import com.example.catalogservice.entity.CatalogEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CatalogRepository extends JpaRepository<CatalogEntity, Long> {
    Optional<CatalogEntity> findByProductId(String productId);
}
