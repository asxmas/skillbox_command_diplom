-- liquibase formatted sql

-- changeset lc:1-1-user
CREATE TABLE "public"."user"
(
    "id"       INTEGER      NOT NULL,
    "name"     VARCHAR(255) NOT NULL,
    "e_mail"   VARCHAR(255) NOT NULL,
    "password" VARCHAR(255) NOT NULL,
    "type"     VARCHAR(25)  NOT NULL,
    CONSTRAINT "user_pk" PRIMARY KEY ("id")
);
COMMENT ON COLUMN "public"."user"."type" IS 'тип пользователя: MODERATOR, ADMIN (может управлять другими админами и модераторами)';
