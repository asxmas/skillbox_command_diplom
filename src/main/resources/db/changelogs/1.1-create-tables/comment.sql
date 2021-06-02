-- liquibase formatted sql

-- changeset lc:1-1-comment
CREATE TABLE "public"."post_comment"
(
    "id"           INTEGER                     NOT NULL,
    "time"         TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    "post_id"      INTEGER                     NOT NULL,
    "parent_id"    INTEGER,
    "author_id"    INTEGER,
    "comment_text" TEXT                        NOT NULL,
    "is_blocked"   BOOLEAN                     NOT NULL,
    CONSTRAINT "post_comment_pk" PRIMARY KEY ("id")
);
COMMENT ON TABLE "public"."post_comment" IS 'комментарий к посту';
COMMENT ON COLUMN "public"."post_comment"."parent_id" IS 'родительский комментарий (если ответ на комментарий к посту)';
COMMENT ON COLUMN "public"."post_comment"."is_blocked" IS 'комментарий заблокирован';
