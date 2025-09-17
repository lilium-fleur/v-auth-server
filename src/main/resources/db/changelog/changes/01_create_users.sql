--liquibase formatted sql

--changeset fleur:1
CREATE TABLE users
(
    id               UUID PRIMARY KEY,
    email            VARCHAR(100) NOT NULL,
    username         VARCHAR(50)  NOT NULL UNIQUE,
    password         VARCHAR(200),
    provider         VARCHAR(50)  NOT NULL,
    provider_user_id VARCHAR(200),
    created_at       TIMESTAMPTZ  NOT NULL,
    last_modified_at TIMESTAMPTZ  NOT NULL,
    UNIQUE (email, provider)
);
--rollback DROP TABLE users