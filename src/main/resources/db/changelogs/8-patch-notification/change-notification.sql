-- liquibase formatted sql

-- changeset MikeVS:7-1 change table notification


ALTER TABLE notification  DROP CONSTRAINT notification_type_id_fk;
DROP TABLE notification_type;
ALTER TABLE notification DROP COLUMN type_id;
ALTER TABLE notification ADD notification_type VARCHAR(25);