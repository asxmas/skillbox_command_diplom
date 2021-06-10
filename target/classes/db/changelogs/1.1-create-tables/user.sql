-- liquibase formatted sql

-- changeset lc:1-1-user
CREATE TABLE "public"."usr"
(
    "id"                INTEGER                     NOT NULL,
    "e_mail"            VARCHAR(255)                NOT NULL,
    "name"              VARCHAR(255)                NOT NULL,
    "password"          VARCHAR(255)                NOT NULL,
    "reg_date"          TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    "type"              VARCHAR(20)                 NOT NULL,
    "person_id"         INTEGER,
    "confirmation_code" VARCHAR(255),
    "is_approved"       BOOLEAN                     NOT NULL,
    CONSTRAINT "usr_pkey" PRIMARY KEY ("id")
);
COMMENT ON COLUMN "public"."usr"."type" IS 'тип пользователя: MODERATOR, ADMIN (может управлять другими админами и модераторами)';
COMMENT ON COLUMN "public"."usr"."confirmation_code" IS 'код восстановления пароля / подтверждения регистрации';
COMMENT ON COLUMN "public"."usr"."is_approved" IS 'подтверждена ли регистрация';
