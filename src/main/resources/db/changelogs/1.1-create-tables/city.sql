-- liquibase formatted sql

-- changeset lc:1-1-city
CREATE TABLE "public"."city"
(
    "id"    INTEGER      NOT NULL,
    "title" VARCHAR(255) NOT NULL,
    CONSTRAINT "city_pkey" PRIMARY KEY ("id")
);
