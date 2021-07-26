-- liquibase formatted sql
-- changeset lc:12.1 comment-column-deleted.sql
alter table post_comment
    add deleted boolean default false not null;
