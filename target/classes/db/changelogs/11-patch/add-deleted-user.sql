-- liquibase formatted sql

-- changeset alexey_pestov:11-add-deleted-user

ALTER TABLE person
    ADD deleted BOOLEAN DEFAULT false;

-- changeset alexey_pestov:11-add-deleted-user-data context:prod
-- first person ('Sebastian Pereira', admin)
UPDATE public.person
SET deleted = false
WHERE id = 2;

INSERT INTO "public"."notified" ("id")
VALUES (13);

INSERT INTO "public"."person" ("first_name", "is_blocked", "last_name", "messages_permission", "id", "email",
                               "reg_date", "deleted")
VALUES ('Inactive', FALSE, 'User', 'ALL', 13, 'inactive', '1799-06-06 22:00:16.000000', FALSE);