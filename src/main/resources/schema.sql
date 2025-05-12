-- Drop previous structures
DROP TABLE IF EXISTS batteries CASCADE;
DROP SEQUENCE IF EXISTS batteries_id_seq;

-- Create sequence with allocationSize = 50 for Hibernate batching
CREATE SEQUENCE batteries_id_seq INCREMENT BY 50 START WITH 1 MINVALUE 1 NO CYCLE;

-- Create batteries table
CREATE TABLE batteries
(
    id         BIGINT PRIMARY KEY DEFAULT NEXTVAL('batteries_id_seq'),
    name       VARCHAR(100) NOT NULL,
    postcode   VARCHAR(10)  NOT NULL,
    capacity   INTEGER      NOT NULL CHECK (capacity >= 0),
    created_at TIMESTAMPTZ        DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMPTZ        DEFAULT CURRENT_TIMESTAMP
);

-- Indexes for performance
CREATE INDEX idx_postcode ON batteries (postcode);
CREATE INDEX idx_capacity ON batteries (capacity);
CREATE INDEX idx_postcode_capacity ON batteries (postcode, capacity);
