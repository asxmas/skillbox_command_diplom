-- liquibase formatted sql
-- changeset lc:13.1-create-notification-settings-table

create table notif_setting
(
    id        int         not null
        constraint notif_setting_pk
            primary key,
    type      varchar(30) not null,
    value     boolean     not null,
    person_id int         not null
        constraint notif_setting_person_id_fk
            references person
);

