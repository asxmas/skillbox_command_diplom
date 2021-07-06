-- liquibase formatted sql

-- changeset asxmas:5-storage
CREATE TABLE "public"."storage"
(
    "id"           INTEGER                     NOT NULL,
    "file_name"    VARCHAR(255)                NOT NULL,
    "relative_file_path" VARCHAR(200)          NOT NULL,
    "raw_file_url" VARCHAR(200)                NOT NULL,
    "file_format"  VARCHAR(50)                 NOT NULL,
    "bytes"        BIGINT                      NOT NULL,
    "file_type"    VARCHAR(50)                 NOT NULL,
    "created_at"   TIMESTAMP WITHOUT TIME ZONE,
    "owner_id"     INTEGER                     NOT NULL,
    "person_id"    INTEGER                     NOT NULL,
    CONSTRAINT "storage_pkey" PRIMARY KEY ("id")
);
COMMENT ON TABLE "public"."storage" IS 'хранилище файлов';
