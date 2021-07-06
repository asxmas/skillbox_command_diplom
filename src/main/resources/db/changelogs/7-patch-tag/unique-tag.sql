-- liquibase formatted sql

-- changeset asxmas:7-unique-tag

alter table tag
    add constraint tag_uindex unique (tag);


