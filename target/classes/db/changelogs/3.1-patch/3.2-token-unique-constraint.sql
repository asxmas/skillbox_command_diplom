-- liquibase formatted sql

-- changeset lc:3.2-token-unique-constraint.sql
create unique index token_black_list_token_uindex
    on token_black_list (token);

