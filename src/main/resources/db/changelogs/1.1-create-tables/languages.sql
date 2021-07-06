-- liquibase formatted sql

-- changeset lc:1-1-city
CREATE TABLE "public"."languages"
(
    "id"    INTEGER      NOT NULL,
    "title" VARCHAR(100) NOT NULL,
    CONSTRAINT "language_pkey" PRIMARY KEY ("id")
);