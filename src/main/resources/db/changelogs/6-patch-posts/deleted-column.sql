-- liquibase formatted sql
-- changeset lc:6-1 deleted column in 'posts' table
alter table post
    add deleted boolean default false not null;

