-- liquibase formatted sql

-- changeset lc:1-1-dialog

CREATE TABLE "public"."dialog"
(
    "id"           INTEGER NOT NULL,
    "unread_count" INTEGER,
    "last_message_id" INTEGER,
    CONSTRAINT "dialog_pkey" PRIMARY KEY ("id")
);
