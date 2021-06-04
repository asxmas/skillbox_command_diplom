-- liquibase formatted sql

-- changeset lc:1-1-country

CREATE TABLE "public"."country"
(
    "id"    INTEGER      NOT NULL,
    "title" VARCHAR(255) NOT NULL,
    CONSTRAINT "country_pkey" PRIMARY KEY ("id")
);
