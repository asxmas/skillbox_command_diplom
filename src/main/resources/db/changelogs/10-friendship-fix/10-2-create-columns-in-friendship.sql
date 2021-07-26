-- liquibase formatted sql

-- changeset lc:10-2-add-new-columns-to-friendship
ALTER TABLE friendship
    ADD "code" VARCHAR(50) NOT NULL default 'REQUEST' ;

ALTER TABLE friendship
    ADD "name" VARCHAR(255) NOT NULL default 'Friendship is magic!';

ALTER TABLE friendship
    ADD "time" TIMESTAMP WITHOUT TIME ZONE NOT NULL default now();
