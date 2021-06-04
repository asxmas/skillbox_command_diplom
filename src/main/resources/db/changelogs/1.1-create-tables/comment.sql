-- liquibase formatted sql

-- changeset lc:1-1-comment
CREATE TABLE "public"."post_comment"
(
    "comment_text" VARCHAR(255)                NOT NULL,
    "is_blocked"   BOOLEAN                     NOT NULL,
    "time"         TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    "id"           INTEGER                     NOT NULL,
    "author_id"    INTEGER                     NOT NULL,
    "parent_id"    INTEGER,
    "post_id"      INTEGER                     NOT NULL,
    CONSTRAINT "post_comment_pkey" PRIMARY KEY ("id")
);
COMMENT ON TABLE "public"."post_comment" IS 'комментарий к посту';
COMMENT ON COLUMN "public"."post_comment"."parent_id" IS 'родительский комментарий (если ответ на комментарий к посту)';
COMMENT ON COLUMN "public"."post_comment"."is_blocked" IS 'комментарий заблокирован';
