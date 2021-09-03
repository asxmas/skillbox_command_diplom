-- liquibase formatted sql

-- changeset lc:1-1-friendship
CREATE TABLE "public"."friendship"
(
    "id"            INTEGER NOT NULL,
    "src_person_id" INTEGER NOT NULL,
    "status_id"     INTEGER NOT NULL,
    "dst_person_id" INTEGER NOT NULL,
    CONSTRAINT "friendship_pkey" PRIMARY KEY ("id")
);
COMMENT ON TABLE "public"."friendship" IS 'дружба';
COMMENT ON COLUMN "public"."friendship"."status_id" IS 'статус связи (см. ниже)';
COMMENT ON COLUMN "public"."friendship"."src_person_id" IS 'пользователь, запросивший дружбу';
COMMENT ON COLUMN "public"."friendship"."dst_person_id" IS 'пользователь, получивший запрос';
