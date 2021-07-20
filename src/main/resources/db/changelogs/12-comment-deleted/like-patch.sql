-- liquibase formatted sql
-- changeset lc:12.2 like-patch

alter table post_like
    add post_id int;

alter table post_like
    add comment_id int;

alter table post_like drop constraint post_like_post_id_fk;

alter table post_like drop column post_comment_id;

alter table post_like
    add constraint like_comment__fk
        foreign key (comment_id) references post_comment;

alter table post_like
    add constraint like_post_id_fk
        foreign key (post_id) references post;

