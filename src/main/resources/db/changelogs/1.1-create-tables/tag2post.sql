-- liquibase formatted sql

-- changeset lc:1-1-tag2post
CREATE TABLE "public"."post2tag"
(
    "post_id" INTEGER NOT NULL,
    "tag_id"  INTEGER NOT NULL,
    CONSTRAINT "post2tag_pkey" PRIMARY KEY ("post_id", "tag_id")
);
COMMENT ON TABLE "public"."post2tag" IS 'связи постов с тэгами';
