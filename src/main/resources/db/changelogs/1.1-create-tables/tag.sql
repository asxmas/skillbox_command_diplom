-- liquibase formatted sql

-- changeset lc:1-1-tag
CREATE TABLE "public"."tag"
(
    "id"  INTEGER      NOT NULL,
    "tag" VARCHAR(255) NOT NULL,
    CONSTRAINT "tag_pkey" PRIMARY KEY ("id")
);
COMMENT ON TABLE "public"."tag" IS 'тэги';
