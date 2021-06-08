-- liquibase formatted sql

-- changeset lc:3-1-blacklisted-token-table

CREATE TABLE "token_black_list"
(
    "id"           INTEGER                     NOT NULL,
    "token"        VARCHAR(1000)               NOT NULL,
    "expired_date" TIMESTAMP WITHOUT TIME ZONE NOT NULL
);
