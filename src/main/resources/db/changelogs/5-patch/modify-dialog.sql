-- liquibase formatted sql
-- changeset pavel:5-3-drop-unread_count

ALTER TABLE dialog
    DROP COLUMN unread_count;
