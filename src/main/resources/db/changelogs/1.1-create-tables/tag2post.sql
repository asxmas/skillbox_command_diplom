-- liquibase formatted sql

-- changeset lc:1-1-tag2post
CREATE TABLE "public"."post2tag"
(
    "id"      INTEGER NOT NULL,
    "post_id" INTEGER NOT NULL,
    "tag_id"  INTEGER NOT NULL,
    CONSTRAINT "post2tag_pk" PRIMARY KEY ("id")
);
COMMENT ON TABLE "public"."post2tag" IS 'связи постов с тэгами';
