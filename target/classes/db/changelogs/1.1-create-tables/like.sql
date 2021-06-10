-- liquibase formatted sql

-- changeset lc:1-1-like
CREATE TABLE "public"."post_like"
(
    "id"        INTEGER NOT NULL,
    "time"      TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    "person_id" INTEGER NOT NULL,
    "post_id"   INTEGER NOT NULL,
    CONSTRAINT "post_like_pkey" PRIMARY KEY ("id")
);
COMMENT
ON TABLE "public"."post_like" IS 'лайки постов';
