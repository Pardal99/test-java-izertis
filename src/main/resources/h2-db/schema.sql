-- ============================================================
-- Table: prices
--------------
-- IMPORTANT: 
-- - We assume that all dates/times stored in this table are in UTC (no timezone).
-- - We assume that prices can overlap for the same brand/product with the same priority beacuase
--   H2 does not support exclusion constraints or interval-based uniqueness.
--   Therefore, it cannot enforce that prices with the same brand, product
--   and priority do not overlap in time.
--   This rule is treated as a domain assumption and is validated at
--   application level; LIMIT 2 is used defensively to detect violations.
-- ============================================================
CREATE TABLE prices (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    brand_id BIGINT NOT NULL,
    start_date TIMESTAMP NOT NULL,
    end_date TIMESTAMP NOT NULL,
    product_id BIGINT NOT NULL,
    price_list INT NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    curr VARCHAR(3) NOT NULL,
    priority INT NOT NULL
);

-- ============================================================
-- Indexes to optimize queries on prices table. It avoids full table scans
-- when searching for prices by brand_id, product_id, and date range.
-- ============================================================
CREATE INDEX idx_prices_brand_product_priority_dates
ON prices (
    brand_id,
    product_id,
    priority DESC,
    start_date,
    end_date
);