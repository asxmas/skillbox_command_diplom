-- liquibase formatted sql
-- changeset lc:1-3-data
INSERT INTO "public"."person" ("id", "first_name", "last_name", "reg_date", "e_mail", "password", "is_approved",
                               "messages_permission", "is_blocked")
VALUES (1, 'john', 'doe', '2021-06-02 17:29:07.13032', 'abc@xyz.com', 'qwerty', TRUE, 'ALL', FALSE);;
