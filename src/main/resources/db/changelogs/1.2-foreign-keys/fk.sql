-- liquibase formatted sql

-- changeset lc:1-2-fk

ALTER TABLE "public"."post_comment"
    ADD CONSTRAINT "post_comment_person_id_fk" FOREIGN KEY ("author_id") REFERENCES "public"."person" ("id") ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE "public"."post_like"
    ADD CONSTRAINT "post_like_person_id_fk" FOREIGN KEY ("person_id") REFERENCES "public"."person" ("id") ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE "public"."post"
    ADD CONSTRAINT "post_person_id_fk" FOREIGN KEY ("author_id") REFERENCES "public"."person" ("id") ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE "public"."usr"
    ADD CONSTRAINT "usr_person_id_fk" FOREIGN KEY ("person") REFERENCES "public"."person" ("id") ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE "public"."post_comment"
    ADD CONSTRAINT "post_comment_post_comment_id_fk" FOREIGN KEY ("parent_id") REFERENCES "public"."post_comment" ("id") ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE "public"."wall_post"
    ADD CONSTRAINT "wall_post_post_id_fk" FOREIGN KEY ("id") REFERENCES "public"."post" ("id") ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE "public"."person"
    ADD CONSTRAINT "person_city_id_fk" FOREIGN KEY ("city") REFERENCES "public"."city" ("id") ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE "public"."person"
    ADD CONSTRAINT "person_country_id_fk" FOREIGN KEY ("country") REFERENCES "public"."country" ("id") ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE "public"."message"
    ADD CONSTRAINT "message_author_id_fk" FOREIGN KEY ("author_id") REFERENCES "public"."person" ("id") ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE "public"."message"
    ADD CONSTRAINT "message_recipient_id_fk" FOREIGN KEY ("recipient_id") REFERENCES "public"."person" ("id") ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE "public"."friendship"
    ADD CONSTRAINT "friendship_dst_id_fk" FOREIGN KEY ("dst_person_id") REFERENCES "public"."person" ("id") ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE "public"."friendship"
    ADD CONSTRAINT "friendship_src_id_fk" FOREIGN KEY ("src_person_id") REFERENCES "public"."person" ("id") ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE "public"."person"
    ADD CONSTRAINT "person_notified_id_fk" FOREIGN KEY ("id") REFERENCES "public"."notified" ("id") ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE "public"."post_comment"
    ADD CONSTRAINT "post_comment_notified_id_fk" FOREIGN KEY ("id") REFERENCES "public"."notified" ("id") ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE "public"."post"
    ADD CONSTRAINT "post_notified_id_fk" FOREIGN KEY ("id") REFERENCES "public"."notified" ("id") ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE "public"."notification"
    ADD CONSTRAINT "notification_notified_id_fk" FOREIGN KEY ("entity_id") REFERENCES "public"."notified" ("id") ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE "public"."notification"
    ADD CONSTRAINT "notification_person_id_fk" FOREIGN KEY ("person_id") REFERENCES "public"."person" ("id") ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE "public"."post2tag"
    ADD CONSTRAINT "post2tag_post_id_fk" FOREIGN KEY ("post_id") REFERENCES "public"."post" ("id") ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE "public"."post_file"
    ADD CONSTRAINT "post_file_post_id_fk" FOREIGN KEY ("post_id") REFERENCES "public"."post" ("id") ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE "public"."post_like"
    ADD CONSTRAINT "post_like_post_id_fk" FOREIGN KEY ("post_id") REFERENCES "public"."post" ("id") ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE "public"."block_history"
    ADD CONSTRAINT "block_history_person_id_fk" FOREIGN KEY ("person_id") REFERENCES "public"."person" ("id") ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE "public"."block_history"
    ADD CONSTRAINT "block_history_post_comment_id_fk" FOREIGN KEY ("comment_id") REFERENCES "public"."post_comment" ("id") ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE "public"."block_history"
    ADD CONSTRAINT "block_history_post_id_fk" FOREIGN KEY ("post_id") REFERENCES "public"."post" ("id") ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE "public"."post_comment"
    ADD CONSTRAINT "post_comment_post_id_fk" FOREIGN KEY ("post_id") REFERENCES "public"."post" ("id") ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE "public"."dialog"
    ADD CONSTRAINT "dialog_message_id_fk" FOREIGN KEY ("last_message") REFERENCES "public"."message" ("id") ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE "public"."message"
    ADD CONSTRAINT "message_notified_id_fk" FOREIGN KEY ("id") REFERENCES "public"."notified" ("id") ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE "public"."friendship"
    ADD CONSTRAINT "friendship_status_id_fk" FOREIGN KEY ("status_id") REFERENCES "public"."friendship_status" ("id") ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE "public"."notification"
    ADD CONSTRAINT "notification_type_id_fk" FOREIGN KEY ("type_id") REFERENCES "public"."notification_type" ("id") ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE "public"."post2tag"
    ADD CONSTRAINT "post2tag_tag_id_fk" FOREIGN KEY ("tag_id") REFERENCES "public"."tag" ("id") ON UPDATE NO ACTION ON DELETE NO ACTION;