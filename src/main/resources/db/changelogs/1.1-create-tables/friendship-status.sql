-- liquibase formatted sql

-- changeset lc:1-1-friendship-status
CREATE TABLE "public"."friendship_status"
(
    "id"   INTEGER                     NOT NULL,
    "code" VARCHAR(255)                NOT NULL,
    "name" VARCHAR(255)                NOT NULL,
    "time" TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    CONSTRAINT "friendship_status_pkey" PRIMARY KEY ("id")
);
COMMENT ON TABLE "public"."friendship_status" IS 'статус дружбы';
COMMENT ON COLUMN "public"."friendship_status"."time" IS 'дата и время установки данного статуса';
COMMENT ON COLUMN "public"."friendship_status"."code" IS 'код статуса
REQUEST - Запрос на добавление в друзья
FRIEND - Друзья
BLOCKED - Пользователь в чёрном списке
DECLINED - Запрос на добавление в друзья отклонён
SUBSCRIBED - Подписан';
