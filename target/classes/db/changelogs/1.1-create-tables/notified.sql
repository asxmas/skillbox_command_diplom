-- liquibase formatted sql

-- changeset lc:1-1-notified

CREATE TABLE "public"."notified"
(
    "id" INTEGER NOT NULL,
    CONSTRAINT "notified_pkey" PRIMARY KEY ("id")
);
