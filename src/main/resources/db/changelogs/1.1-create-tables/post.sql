-- liquibase formatted sql

-- changeset lc:1-1-post
CREATE TABLE "public"."post"
(
    "id"         INTEGER                     NOT NULL,
    "time"       TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    "author_id"  INTEGER                     NOT NULL,
    "title"      VARCHAR(1000)               NOT NULL,
    "post_text"  TEXT                        NOT NULL,
    "is_blocked" BOOLEAN                     NOT NULL,
    CONSTRAINT "post_pk" PRIMARY KEY ("id")
);
COMMENT ON TABLE "public"."post" IS 'посты';
COMMENT ON COLUMN "public"."post"."time" IS 'дата и время публикации';
COMMENT ON COLUMN "public"."post"."title" IS 'заголовок';
COMMENT ON COLUMN "public"."post"."post_text" IS 'HTML-текст поста';
COMMENT ON COLUMN "public"."post"."is_blocked" IS 'отметка о том, что пост заблокирован';
