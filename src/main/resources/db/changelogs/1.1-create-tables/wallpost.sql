-- liquibase formatted sql

-- changeset lc:1-1-wallpost
CREATE TABLE "public"."wall_post"
(
    "type" VARCHAR(255),
    "id"   INTEGER NOT NULL,
    CONSTRAINT "wall_post_pkey" PRIMARY KEY ("id")
);
