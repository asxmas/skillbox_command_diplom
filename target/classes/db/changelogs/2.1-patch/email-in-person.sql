-- liquibase formatted sql
-- changeset lc:2-2-email-column-in-person
ALTER TABLE person
    ADD email VARCHAR(255);

UPDATE public.person
SET email = 'admin@example.com'
WHERE id = 2;

ALTER TABLE person
    ALTER COLUMN email SET NOT NULL;

