-- liquibase formatted sql

-- changeset lc:1-2-person

ALTER TABLE person
    ADD is_archive BOOLEAN NOT NULL DEFAULT false;

UPDATE public.person
SET is_archive = true
WHERE id = 13;