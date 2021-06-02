-- liquibase formatted sql

-- changeset lc:1-1-message
CREATE TABLE "public"."message"
(
    "id"           INTEGER                     NOT NULL,
    "time"         TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    "author_id"    INTEGER                     NOT NULL,
    "recipient_id" INTEGER                     NOT NULL,
    "message_text" TEXT                        NOT NULL,
    "read_status"  VARCHAR(25)                 NOT NULL,
    CONSTRAINT "message_pk" PRIMARY KEY ("id")
);
COMMENT ON TABLE "public"."message" IS 'личные сообщения';
COMMENT ON COLUMN "public"."message"."time" IS 'дата и время отправки';
