-- liquibase formatted sql
-- changeset lc:2-1-move-regdate

ALTER TABLE usr
    DROP COLUMN reg_date;

ALTER TABLE person
    ADD reg_date TIMESTAMP WITHOUT TIME ZONE;

UPDATE public.person
SET reg_date = '2021-06-06 22:00:16.000000'
WHERE id = 2;

ALTER TABLE person
    ALTER COLUMN reg_date SET NOT NULL;
