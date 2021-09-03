-- liquibase formatted sql

-- changeset lc:14-1-test-data context:tests
INSERT INTO "notified" ("id")
VALUES (2),(3),(13),(1000),(1006),(1007),(1010),(1012),(1015),(1022),(1023),(1024),(1025),(1026),(1027),(1028),
       (1029),(1030),(1031),(1037),(1040),(1048),(1049),(1051),(1052),(1177),(1180),(1189),(1194),(1198),(1201),
       (1236),(1243),(1246),(1248),(1250),(1253),(1289),(1295),(1302),(1304),(1307),(1309),(1086),(1089),(1092),
       (1096),(1102),(1106),(1110),(1120),(1126),(1133),(1136),(1138),(1140),(1142),(1146),(1162),(1167), (5000),
       (5001), (5002),(5003),(5004),(5005),(5006),(5007),(5008);

INSERT INTO "country" ("id", "title")
VALUES (1, 'country 1'),
       (2, 'country 2'),
       (1005, 'country 3'),
       (1035, 'country 4'),
       (1044, 'country 5');

INSERT INTO "city" ("id", "title")
VALUES (1, 'city 1'),
       (2, 'city 2'),
       (1004, 'city 3'),
       (1034, 'city 4'),
       (1043, 'city 5');

INSERT INTO "person" ("about", "birth_date", "first_name", "is_blocked", "last_name", "last_online_time", "messages_permission", "phone", "photo", "id", "city_id", "country_id", "email", "reg_date", "deleted")
VALUES (NULL, '1999-09-01 00:00:00', 'Bill', FALSE, 'Billson', '2021-08-10 10:00:00.000000', 'ALL', NULL, '/pic/duser.png', 2, NULL, NULL, 'a1@b.com', '2021-06-06 22:00:16', FALSE),
       (NULL, '1988-05-01 00:00:00', 'Bob', FALSE, 'Bobson', '2021-08-01 10:00:00.000000', 'ALL', NULL, '/pic/duser.png', 13, NULL, NULL, 'a2@b.com', '2020-06-06 22:00:16', FALSE),
       (NULL, '2000-10-01 00:00:00', 'Tom', FALSE, 'Tomson', '2021-08-13 10:27:01.929359', 'ALL', '', '/pic/duser.png', 1015, 1034, 1035, 'a3@b.com', '2021-08-13 10:27:01.92935', FALSE),
       (NULL, '1988-02-01 00:00:00', 'Jim', FALSE, 'Jimson', '2021-08-13 11:03:15.877837', 'ALL', '', '/pic/duser.png', 1040, 1043, 1044, 'a4@b.com', '2021-08-13 11:03:15.877833', FALSE),
       (NULL, '2000-02-01 00:00:00', 'Nick', FALSE, 'Nickson', '2021-08-16 23:09:23.638773', 'ALL', NULL, '/pic/duser.png', 1110, NULL, NULL, 'a5@b.com', '2021-08-16 23:09:23.638755', FALSE),
       ('about', '1970-08-18 00:00:00', 'Rick', FALSE, 'Rickson', '2021-08-13 10:17:15.510554', 'ALL', '71234567890', '/pic/duser.png', 1000, 1004, 1005, 'a6@b.com', '2021-08-13 10:17:15.510543', FALSE),
       (NULL, '2005-01-01 00:00:00', 'Dick', FALSE, 'Dickson', '2021-08-18 15:08:07.877327', 'ALL', NULL, '/pic/duser.png', 1146, NULL, NULL, 'a7@b.com', '2021-08-18 15:08:07.877307', FALSE),
       (NULL, '1999-10-01 00:00:00', 'Tim', FALSE, 'Timson', '2021-08-18 15:08:07.877327', 'ALL', NULL, '/pic/duser.png', 5002, NULL, NULL, 'a8@b.com', '2021-08-18 15:08:07.877307', TRUE);


INSERT INTO "friendship" ("id", "src_person_id", "dst_person_id", "code", "name", "time")
VALUES (1164, 1000, 1146, 'FRIEND', 'Friendship is magic!', '2021-08-18 15:15:44.840889'),
       (1222, 1146, 1000, 'FRIEND', 'Friendship is magic!', '2021-08-18 16:26:10.2797'),
       (1255, 1040, 1146, 'FRIEND', 'Friendship is magic!', '2021-08-27 00:46:38.312874'),
       (1262, 1110, 1146, 'FRIEND', 'Friendship is magic!', '2021-08-27 01:18:08.160419'),
       (1263, 1110, 1000, 'FRIEND', 'Friendship is magic!', '2021-08-27 01:18:08.160419'),
       (1264, 1110, 13, 'BLOCKED', 'Friendship is magic!', '2021-08-27 01:18:08.160419'),
       (1265, 1110, 5002, 'FRIEND', 'Friendship is magic!', '2021-08-27 01:18:08.160419');

INSERT INTO "usr" ("id", "e_mail", "name", "password", "type", "person_id", "confirmation_code", "is_approved")
VALUES (1, 'a1@b.com', 'admin', 'password', 'ADMIN', 2, NULL, TRUE),
       (1016, 'a2@b.com', 'noname', '$2a$12$sDBbN/hLKDhIxpykOv8sMuz/EX.73Uj/r1r8Z0ixh2sVDubYJ8TMu', 'USER', 13, '3675', TRUE),
       (1017, 'a3@b.com', 'noname', '$2a$12$sDBbN/hLKDhIxpykOv8sMuz/EX.73Uj/r1r8Z0ixh2sVDubYJ8TMu', 'USER', 1015, '3675', TRUE),
       (1041, 'a4@b.com', 'noname', '$2a$12$w4qBt6vfaEaCLmoUR8TFduQ7YLN0bsFvSYQEQ5L/SIOKoRuHYBSCW', 'USER', 1040, '3675', TRUE),
       (1111, 'a5@b.com', 'noname', '$2a$12$kwjhEXznvR4/NjCfdTuVPu.D1MWaf1YL3jeDpGJ/s5FJnEAhTWsqu', 'USER', 1110, '3675', TRUE),
       (1001, 'a6@b.com', 'noname', '$2a$12$rjEqFf4VEg7.m.ajAChgSOs3dV.lz2wbPo6J4Ivs/WGxuwOUEMjkG', 'USER', 1000, '3675', TRUE),
       (1147, 'a7@b.com', 'noname', '$2a$12$HSpb.Fz6ViJDzmTYXvr/y.IgU5Iy4n6cQg4tYvYgWqGJBknG2Y2xK', 'USER', 1146, '3675', TRUE),
       (4999, 'a8@b.com', 'noname', '$2a$12$HSpb.Fz6ViJDzmTYXvr/y.IgU5Iy4n6cQg4tYvYgWqGJBknG2Y2xK', 'USER', 5002, '3675', TRUE);

INSERT INTO "languages" ("id", "title")
VALUES (1, 'Russian'),
       (2, 'English');

INSERT INTO "post" ("is_blocked", "post_text", "time", "title", "id", "author_id", "deleted")
VALUES (FALSE, 'text 1', '2021-06-04 19:45:03.12229', 'title 1', 3, 2, FALSE),
       (FALSE, 'text 2', '2030-08-13 10:20:30.67194', 'title 2', 1006, 1000, FALSE),
       (FALSE, 'text 3', '2030-08-13 10:31:56.611287', 'title 3', 1030, 1000, FALSE),
       (FALSE, 'text 4', '2021-08-13 10:24:49.064618', 'title 4', 1010, 1000, FALSE),
       (FALSE, 'text 5', '2021-08-16 21:05:12.412425', 'title 5', 1092, 1040, TRUE),
       (FALSE, 'text 6', '2021-08-16 21:02:44.283596', 'title 6', 1089, 1040, TRUE),
       (FALSE, 'text 7', '2021-08-16 21:00:26.146405', 'title 7', 1086, 1040, TRUE),
       (FALSE, 'text 8', '2021-08-16 22:41:40.698234', 'title 8', 1102, 1040, FALSE),
       (FALSE, 'text 9', '2021-08-16 23:37:48.100003', 'title 9', 1126, 1040, FALSE),
       (FALSE, 'text 10', '2021-08-18 15:53:32.736118', 'title 10', 1180, 1146, FALSE),
       (FALSE, 'teSUBSTRINGxt 11', '2021-08-18 16:09:14.140796', 'title 11', 1189, 1146, FALSE),
       (FALSE, 'text 12', '2021-08-26 23:01:00.520468', 'title 12', 1236, 1000, FALSE),
       (FALSE, 'text 13', '2021-08-27 01:32:53.833039', 'title 13  __subString*', 1289, 1146, FALSE),
       (FALSE, 'text 14', '2021-08-27 01:33:41.701199', 'title 14', 1295, 1146, FALSE),
       (FALSE, 'text 15', '2021-08-27 01:33:41.701199', 'title 15', 5000, 2, FALSE),
       (FALSE, 'text 15', '2021-08-27 01:33:41.701199', 'title 15', 5001, 2, FALSE),
       (FALSE, 'post from deleted user', '2021-08-27 01:33:41.701199', 'deleted user', 5008, 5002, FALSE);

INSERT INTO "post_comment" ("comment_text", "is_blocked", "time", "id", "author_id", "parent_id", "post_id", "deleted")
VALUES ('comment 1', FALSE, '2021-08-13 10:20:51.261797', 1007, 1000, NULL, 1006, TRUE),
       ('comment 2', FALSE, '2021-08-13 10:25:38.357378', 1012, 1000, NULL, 1010, FALSE),
       ('comment 3', FALSE, '2021-08-13 10:32:29.932382', 1031, 1000, NULL, 1030, FALSE),
       ('comment 4', FALSE, '2021-08-13 10:36:16.073485', 1037, 1000, NULL, 1030, TRUE),
       ('comment 5', FALSE, '2021-08-13 11:09:38.469415', 1051, 1040, NULL, 1030, FALSE),
       ('comment 6', FALSE, '2021-08-13 11:09:50.419764', 1052, 1040, 1012, 1010, FALSE),
       ('comment 7', FALSE, '2021-08-16 21:06:16.605531', 1096, 1000, NULL, 1092, FALSE),
       ('comment 8', FALSE, '2021-08-16 22:43:11.157331', 1106, 1000, NULL, 1102, FALSE),
       ('comment 9', FALSE, '2021-08-16 23:34:33.681348', 1120, 1110, 1012, 1102, FALSE),
       ('comment 10', FALSE, '2021-08-18 16:09:49.961464', 1194, 1000, NULL, 1189, FALSE),
       ('comment 11', FALSE, '2021-08-18 16:10:30.531848', 1198, 1146, 1194, 1189, FALSE),
       ('comment 12', FALSE, '2021-08-27 00:40:24.767889', 1243, 1000, NULL, 1236, FALSE),
       ('comment 13', FALSE, '2021-08-27 00:41:29.35358', 1246, 1040, 1243, 1236, FALSE),
       ('comment 14', FALSE, '2021-08-27 00:42:28.521199', 1248, 1000, NULL, 1126, FALSE),
       ('comment 15', FALSE, '2021-08-27 00:42:28.521199', 5007, 1000, NULL, 1236, TRUE);

INSERT INTO "post_like" ("id", "time", "person_id", "post_id", "comment_id")
VALUES (1008, '2021-08-13 10:20:53.808184', 1000, 1006, NULL),
       (1009, '2021-08-13 10:20:54.947304', 1000, NULL, 1007),
       (1013, '2021-08-13 10:25:41.510045', 1000, 1010, NULL),
       (1014, '2021-08-13 10:25:42.725131', 1000, NULL, 1012),
       (1032, '2021-08-13 10:32:46.605383', 1015, 1006, NULL),
       (1038, '2021-08-13 10:36:25.148683', 1000, NULL, 1120),
       (1053, '2021-08-13 11:09:53.615311', 1040, 1030, NULL),
       (1054, '2021-08-13 11:10:02.822831', 1040, 1006, NULL),
       (5003, '2021-08-13 11:10:02.822831', 1110, NULL, 1007),
       (5004, '2021-08-13 11:10:02.822831', 1110, 3, NULL),
       (5005, '2021-08-13 11:10:02.822831', 1110, NULL, 1120),
       (5006, '2021-08-13 11:10:02.822831', 1110, NULL, 1194);

INSERT INTO "tag" ("id", "tag")
VALUES (4, 'tag 1'),
       (5, 'tag 2'),
       (1011, 'tag 3'),
       (1018, 'tag 4'),
       (1019, 'tag 5'),
       (1020, 'tag 6');

INSERT INTO "post2tag" ("post_id", "tag_id")
VALUES (3, 4),
       (3, 5),
       (1010, 1011),
       (1010, 1018),
       (1006, 1018),
       (1006, 1019);

