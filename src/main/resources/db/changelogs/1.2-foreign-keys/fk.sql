-- liquibase formatted sql

-- changeset lc:1-2-fk

ALTER TABLE "public"."post_comment"
    ADD CONSTRAINT "post_comment_person_id_fk" FOREIGN KEY ("author_id") REFERENCES "public"."person" ("id") ON UPDATE NO ACTION ON DELETE NO ACTION;

ALTER TABLE "public"."post_like"
    ADD CONSTRAINT "post_like_person_id_fk" FOREIGN KEY ("person_id") REFERENCES "public"."person" ("id") ON UPDATE NO ACTION ON DELETE NO ACTION;

ALTER TABLE "public"."post"
    ADD CONSTRAINT "post_person_id_fk" FOREIGN KEY ("author_id") REFERENCES "public"."person" ("id") ON UPDATE NO ACTION ON DELETE NO ACTION;

ALTER TABLE "public"."message"
    ADD CONSTRAINT "message_author_person_id_fk" FOREIGN KEY ("author_id") REFERENCES "public"."person" ("id") ON UPDATE NO ACTION ON DELETE NO ACTION;

ALTER TABLE "public"."message"
    ADD CONSTRAINT "message_recipient_person_id_fk" FOREIGN KEY ("recipient_id") REFERENCES "public"."person" ("id") ON UPDATE NO ACTION ON DELETE NO ACTION;

ALTER TABLE "public"."notification"
    ADD CONSTRAINT "notification_person_id_fk" FOREIGN KEY ("person_id") REFERENCES "public"."person" ("id") ON UPDATE NO ACTION ON DELETE NO ACTION;

ALTER TABLE "public"."post_comment"
    ADD CONSTRAINT "post_comment_post_comment_id_fk" FOREIGN KEY ("parent_id") REFERENCES "public"."post_comment" ("id") ON UPDATE NO ACTION ON DELETE NO ACTION;

ALTER TABLE "public"."post_comment"
    ADD CONSTRAINT "post_comment_post_id_fk" FOREIGN KEY ("post_id") REFERENCES "public"."post" ("id") ON UPDATE NO ACTION ON DELETE NO ACTION;

ALTER TABLE "public"."post_file"
    ADD CONSTRAINT "post_file_post_id_fk" FOREIGN KEY ("post_id") REFERENCES "public"."post" ("id") ON UPDATE NO ACTION ON DELETE NO ACTION;

ALTER TABLE "public"."post_like"
    ADD CONSTRAINT "post_like_post_id_fk" FOREIGN KEY ("post_id") REFERENCES "public"."post" ("id") ON UPDATE NO ACTION ON DELETE NO ACTION;

ALTER TABLE "public"."block_history"
    ADD CONSTRAINT "block_history_person_id_fk" FOREIGN KEY ("id") REFERENCES "public"."person" ("id") ON UPDATE NO ACTION ON DELETE NO ACTION;

ALTER TABLE "public"."post2tag"
    ADD CONSTRAINT "post2tag_post_id_fk" FOREIGN KEY ("post_id") REFERENCES "public"."post" ("id") ON UPDATE NO ACTION ON DELETE NO ACTION;

ALTER TABLE "public"."post2tag"
    ADD CONSTRAINT "post2tag_tag_id_fk" FOREIGN KEY ("tag_id") REFERENCES "public"."tag" ("id") ON UPDATE NO ACTION ON DELETE NO ACTION;
