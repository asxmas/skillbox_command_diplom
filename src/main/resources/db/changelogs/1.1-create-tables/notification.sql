-- liquibase formatted sql

-- changeset lc:1-1-notification
CREATE TABLE "public"."notification"
(
    "id"        INTEGER                     NOT NULL,
    "type_id"   INTEGER                     NOT NULL,
    "sent_time" TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    "person_id" INTEGER                     NOT NULL,
    "entity_id" INTEGER                     NOT NULL,
    "contact"   VARCHAR(255)                NOT NULL,
    CONSTRAINT "notification_pk" PRIMARY KEY ("id")
);
COMMENT ON TABLE "public"."notification" IS 'оповещения';
COMMENT ON COLUMN "public"."notification"."type_id" IS 'тип оповещения';
COMMENT ON COLUMN "public"."notification"."sent_time" IS 'время отправки';
COMMENT ON COLUMN "public"."notification"."person_id" IS 'кому отправлено';
COMMENT ON COLUMN "public"."notification"."entity_id" IS 'идентификатор сущности, относительно которой отправлено оповещение (комментарий, друг, пост или сообщение)';
