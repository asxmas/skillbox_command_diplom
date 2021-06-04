-- liquibase formatted sql

-- changeset lc:1-1-notification
CREATE TABLE "public"."notification"
(
    "descriminator_type" VARCHAR(31)                 NOT NULL,
    "id"                 INTEGER                     NOT NULL,
    "info"               VARCHAR(255)                NOT NULL,
    "sent_time"          TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    "contact"            VARCHAR(255),
    "entity_id"          INTEGER                     NOT NULL,
    "type_id"            INTEGER                     NOT NULL,
    "person_id"          INTEGER,
    CONSTRAINT "notification_pkey" PRIMARY KEY ("id")
);
COMMENT ON TABLE "public"."notification" IS 'оповещения';
COMMENT ON COLUMN "public"."notification"."type_id" IS 'тип оповещения';
COMMENT ON COLUMN "public"."notification"."sent_time" IS 'время отправки';
COMMENT ON COLUMN "public"."notification"."person_id" IS 'кому отправлено';
COMMENT ON COLUMN "public"."notification"."entity_id" IS 'идентификатор сущности, относительно которой отправлено оповещение (комментарий, друг, пост или сообщение)';
COMMENT ON COLUMN "public"."notification"."contact" IS ' куда отправлено оповещение (конкретный e-mail или телефон)';
