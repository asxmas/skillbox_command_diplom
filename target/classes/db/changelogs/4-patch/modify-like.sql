-- liquibase formatted sql

-- changeset lc:4.0-rename-like-column
alter table post_like
    rename column post_id to post_comment_id;

alter table post_like
    drop constraint post_like_post_id_fk;

alter table post_like
    add constraint post_like_post_id_fk
        foreign key (post_comment_id) references post;

