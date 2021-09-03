-- liquibase formatted sql
-- changeset lc:13.2-recreate-subscription-table

DROP TABLE if exists subscription;

CREATE TABLE subscription
(
    id            INT         NOT NULL
        CONSTRAINT subscription_pk
            PRIMARY KEY,
    subscriber_id int         NOT NULL
        CONSTRAINT subscription_person_id_fk
            REFERENCES person,
    source_id     INT         NOT NULL
        CONSTRAINT subscription_notified_id_fk
            REFERENCES notified,
    type          VARCHAR(30) NOT NULL
);

