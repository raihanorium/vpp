DROP TABLE IF EXISTS batteries;

CREATE TABLE IF NOT EXISTS batteries
(
    id BIGSERIAL PRIMARY KEY,
    name       VARCHAR(100) NOT NULL,
    postcode   VARCHAR(10)  NOT NULL,
    capacity   INTEGER      NOT NULL CHECK (capacity >= 0),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    -- Indexes
    INDEX idx_postcode (postcode),
    INDEX idx_capacity (capacity),
    INDEX idx_postcode_capacity (postcode, capacity)
);
