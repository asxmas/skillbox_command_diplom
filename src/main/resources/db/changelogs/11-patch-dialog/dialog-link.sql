-- liquibase formatted sql
-- changeset pavel:11-0-link-in-dialog
ALTER TABLE dialog
    ADD invite_link VARCHAR(100);