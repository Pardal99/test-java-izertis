package com.enterprise.ppardal.infrastructure.adapter.persistence;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.enterprise.ppardal.infrastructure.adapter.persistence.model.PriceEntity;

@Repository
public interface PricesRepository extends JpaRepository<PriceEntity, Long> {

    @Query(value = "SELECT * FROM prices WHERE brand_id = :brandId AND product_id = :productId AND start_date <= :applicationDate AND end_date > :applicationDate ORDER BY priority DESC LIMIT 2", nativeQuery = true)
    List<PriceEntity> findTop2ByBrandIdAndProductIdAndApplicationDate(
        @Param("brandId") Long brandId,
        @Param("productId") Long productId,
        @Param("applicationDate") LocalDateTime applicationDate
    );

}