INSERT INTO operation (id, expression, result, enterDate, userId)
VALUES ('6cb73b30-eef1-4704-a7b7-77787123ea84', '1+1', '2', '2021-02-05', 1),
       ('6cb73b30-eef1-4704-a7b7-77787123ea85', '1*1+3', '4', '2021-02-06', 1),
       ('6cb73b30-eef1-4704-a7b7-77787123ea86', '77-7', '70', '2021-02-07', 2);


INSERT INTO role (id, name)
VALUES (1, 'USER');

INSERT INTO usr (id, username, password, active)
VALUES (1, 'test', 'test', TRUE);

INSERT INTO user_role
VALUES (1, 'USER');