-- liquibase formatted sql

-- changeset lc:1-1-person

CREATE TABLE "public"."person"
(
    "about"               VARCHAR(255),
    "birth_date"          TIMESTAMP WITHOUT TIME ZONE,
    "first_name"          VARCHAR(255) NOT NULL,
    "is_blocked"          BOOLEAN      NOT NULL,
    "last_name"           VARCHAR(255) NOT NULL,
    "last_online_time"    TIMESTAMP WITHOUT TIME ZONE,
    "messages_permission" VARCHAR(255) NOT NULL,
    "phone"               VARCHAR(255),
    "photo"               VARCHAR(255),
    "id"                  INTEGER      NOT NULL,
    "city"                INTEGER,
    "country"             INTEGER,
    CONSTRAINT "person_pkey" PRIMARY KEY ("id")
);

COMMENT ON TABLE "public"."person" IS 'пользователь соц. сети';
COMMENT ON COLUMN "public"."person"."first_name" IS 'имя';
COMMENT ON COLUMN "public"."person"."last_name" IS 'фамилия';
-- COMMENT ON COLUMN "public"."person"."reg_date" IS 'дата и время регистрации';
COMMENT ON COLUMN "public"."person"."birth_date" IS 'дата рождения';
COMMENT ON COLUMN "public"."person"."photo" IS 'ссылка на изображение';
COMMENT ON COLUMN "public"."person"."about" IS 'текст о себе';
COMMENT ON COLUMN "public"."person"."city" IS 'страна и город проживания';
-- COMMENT ON COLUMN "public"."person"."confirmation_code" IS 'код восстановления пароля / подтверждения регистрации';
-- COMMENT ON COLUMN "public"."person"."is_approved" IS 'подтверждена ли регистрация';
COMMENT ON COLUMN "public"."person"."messages_permission" IS 'разрешение на получение сообщений: ALL - от всех пользователей (кроме заблокированных), FRIENDS - только от друзей';
COMMENT ON COLUMN "public"."person"."last_online_time" IS 'время последнего пребывания онлайн';
COMMENT ON COLUMN "public"."person"."is_blocked" IS 'блокировка пользователя модератором / администратором';
