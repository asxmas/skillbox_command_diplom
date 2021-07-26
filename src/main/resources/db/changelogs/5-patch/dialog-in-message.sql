-- liquibase formatted sql
-- changeset pavel:5-1-dialog-column-in-message
ALTER TABLE message
    ADD dialog_id INTEGER;

ALTER TABLE "public"."message"
ADD CONSTRAINT "message_dialog_id_fk" FOREIGN KEY ("dialog_id") REFERENCES "public"."dialog" ("id") ON UPDATE NO ACTION ON DELETE NO ACTION;
