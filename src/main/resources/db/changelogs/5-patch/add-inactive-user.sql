-- liquibase formatted sql

-- changeset lc:1-2-person

ALTER TABLE person
    ADD is_archive BOOLEAN NOT NULL DEFAULT false;

UPDATE public.person
SET is_archive = true
WHERE id = 13;

INSERT INTO "public"."notified" ("id")
VALUES (13);

INSERT INTO "public"."person" ("first_name", "is_blocked", "last_name", "messages_permission", "id","email","reg_date")
VALUES ('Inactive', FALSE, 'User', 'ALL', 13, 'inactive', '1799-06-06 22:00:16.000000');