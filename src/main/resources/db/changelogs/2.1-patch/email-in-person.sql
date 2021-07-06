-- liquibase formatted sql
-- changeset lc:2-2-email-column-in-person
ALTER TABLE person
    ADD email VARCHAR(255);

UPDATE public.person
SET email = 'admin@example.com'
WHERE id = 2;

UPDATE public.person
SET email = 'inactive@yandex.ru'
WHERE id = 13;

ALTER TABLE person
    ALTER COLUMN email SET NOT NULL;

