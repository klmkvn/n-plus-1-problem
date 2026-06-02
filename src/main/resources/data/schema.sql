-- ============================================================
-- Schema — used by docker-compose and dev profile
-- ============================================================

DROP TABLE IF EXISTS books;
DROP TABLE IF EXISTS authors;

CREATE TABLE authors (
    id      BIGSERIAL    PRIMARY KEY,
    name    VARCHAR(255) NOT NULL,
    country VARCHAR(100)
);

CREATE TABLE books (
    id        BIGSERIAL    PRIMARY KEY,
    title     VARCHAR(255) NOT NULL,
    year      INT,
    author_id BIGINT       NOT NULL REFERENCES authors(id) ON DELETE CASCADE
);
