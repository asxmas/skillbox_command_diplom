-- liquibase formatted sql

-- changeset lc:10-1-drop-friendship-status
ALTER TABLE friendship DROP CONSTRAINT friendship_status_id_fk;

ALTER TABLE friendship DROP COLUMN status_id;

DROP TABLE friendship_status;
