-- liquibase formatted sql
-- changeset lc:13.3-recreate-notification-table

DROP TABLE if exists notification;

create table notification
(
    id          INT         NOT NULL
        constraint notification_pk
            PRIMARY KEY,
    receiver_id INT         NOT NULL
        CONSTRAINT notification_person_id_fk
            REFERENCES person,
    type        VARCHAR(30) NOT NULL,
    text        TEXT,
    time        timestamp   NOT NULL,
    read        BOOLEAN,
    author_id   INT,
    source_id   INT
        CONSTRAINT notification_notified_id_fk
            REFERENCES notified
);

