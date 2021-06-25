-- liquibase formatted sql

-- changeset pavel:5-2-dialog2person-table

CREATE TABLE "public"."dialog2person"
(
    "id"         INTEGER NOT NULL,
    "dialog_id"  INTEGER NOT NULL,
    "person_id"  INTEGER NOT NULL,
    CONSTRAINT "dialog2person_pkey" PRIMARY KEY ("dialog_id", "person_id")
);
COMMENT ON TABLE "public"."dialog2person" IS 'связи ползователей с диалогами';

ALTER TABLE "public"."dialog2person"
ADD CONSTRAINT "dialog2person_dialog_id_fk" FOREIGN KEY ("dialog_id") REFERENCES "public"."dialog" ("id") ON UPDATE NO ACTION ON DELETE NO ACTION;

ALTER TABLE "public"."dialog2person"
ADD CONSTRAINT "dialog2person_person_id_fk" FOREIGN KEY ("person_id") REFERENCES "public"."person" ("id") ON UPDATE NO ACTION ON DELETE NO ACTION;

