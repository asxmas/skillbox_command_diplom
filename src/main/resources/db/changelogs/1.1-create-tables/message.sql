-- liquibase formatted sql

-- changeset lc:1-1-message
CREATE TABLE "public"."message"
(
    "message_text" VARCHAR(255)                NOT NULL,
    "read_status"  INTEGER                     NOT NULL,
    "time"         TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    "id"           INTEGER                     NOT NULL,
    "author_id"    INTEGER                     NOT NULL,
    "recipient_id" INTEGER                     NOT NULL,
    CONSTRAINT "message_pkey" PRIMARY KEY ("id")
);
COMMENT ON TABLE "public"."message" IS 'личные сообщения';
COMMENT ON COLUMN "public"."message"."time" IS 'дата и время отправки';
COMMENT ON COLUMN message.message_text IS 'статус прочтения: SENT (не прочитано) и READ (прочитано)';