-- liquibase formatted sql
-- changeset lc:1-3-data

INSERT INTO "public"."notified" ("id")
VALUES (2), (3), (13);

INSERT INTO "public"."person" ("first_name", "is_blocked", "last_name", "messages_permission", "id")
VALUES ('Sebastian', FALSE, 'Pereira', 'ALL', 2),
       ('Inactive', FALSE, 'User', 'ALL', 13);

INSERT INTO "public"."usr" ("id", "e_mail", "name", "password", "reg_date", "type", "person_id", "is_approved")
VALUES (1, 'admin@example.com', 'admin', 'password', '2021-06-04 19:45:03.06865', 'ADMIN', 2, TRUE);;

INSERT INTO "public"."post" ("is_blocked", "post_text", "time", "title", "id", "author_id")
VALUES (FALSE, 'subj!', '2021-06-04 19:45:03.12229', 'Hello world!', 3, 2);;

INSERT INTO "public"."notification_type" ("id", "code", "name")
VALUES (7, 'POST', 'post notification'), (9, 'FRIEND_REQUEST', 'friend notification');;

INSERT INTO "public"."notification" ("descriminator_type", "id", "info", "sent_time", "entity_id", "type_id",
                                     "person_id")
VALUES ('FULL', 6, 'post info', '2021-06-04 19:45:03.163564', 3, 7, 2),
       ('FULL', 8, 'friend info', '2021-06-04 19:45:03.168984', 2, 9, 2);;

INSERT INTO "public"."tag" ("id", "tag")
VALUES (4, 'tag two'), (5, 'tag one');;

INSERT INTO "public"."post2tag" ("post_id", "tag_id")
VALUES (3, 4), (3, 5);;
