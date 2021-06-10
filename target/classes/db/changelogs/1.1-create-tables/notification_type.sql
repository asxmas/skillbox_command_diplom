-- liquibase formatted sql

-- changeset lc:1-1-notification-type
CREATE TABLE "public"."notification_type"
(
    "id"   INTEGER      NOT NULL,
    "code" VARCHAR(30)  NOT NULL,
    "name" VARCHAR(255) NOT NULL,
    CONSTRAINT "notification_type_pkey" PRIMARY KEY ("id")
);
COMMENT ON TABLE "public"."notification_type" IS 'тип оповещения';
COMMENT ON COLUMN "public"."notification_type"."code" IS 'код типа';
COMMENT ON COLUMN "public"."notification_type"."name" IS 'POST - Новый пост
POST_COMMENT - Комментарий к посту
COMMENT_COMMENT - Ответ на комментарий
FRIEND_REQUEST - Запрос дружбы
MESSAGE - Личное сообщение';
