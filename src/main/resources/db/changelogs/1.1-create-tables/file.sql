-- liquibase formatted sql

-- changeset lc:1-1-file
CREATE TABLE "public"."post_file"
(
    "id"      INTEGER      NOT NULL,
    "name"    VARCHAR(255),
    "path"    VARCHAR(255) NOT NULL,
    "post_id" INTEGER      NOT NULL,
    CONSTRAINT "post_file_pkey" PRIMARY KEY ("id")
);
COMMENT ON TABLE "public"."post_file" IS 'файлы, прикреплённые к постам';
COMMENT ON COLUMN "public"."post_file"."path" IS 'путь к файлу в хранилище';
