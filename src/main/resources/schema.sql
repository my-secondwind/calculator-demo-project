DROP TABLE IF EXISTS operation;

CREATE TABLE operation
(
    id                 UUID PRIMARY KEY,
    expression         VARCHAR(250) NOT NULL,
    result             VARCHAR(250) NOT NULL,
    enterDate          DATE       NOT NULL,
    userId             LONG        NOT NULL
);

DROP TABLE IF EXISTS user_role;

CREATE TABLE role
(
    id          LONG PRIMARY KEY,
    name        VARCHAR(250) NOT NULL
);

DROP TABLE IF EXISTS usr;

CREATE TABLE usr
(
    id                 LONG PRIMARY KEY,
    username           VARCHAR(250) NOT NULL,
    password           VARCHAR(250) NOT NULL,
    active             BOOLEAN
);

DROP TABLE IF EXISTS user_role;

CREATE TABLE user_role
(
   user_id  LONG NOT NULL,
   role_name  VARCHAR(250) NOT NULL
);

ALTER TABLE user_role
    ADD FOREIGN KEY (user_id)
        REFERENCES usr(id);

ALTER TABLE user_role
     ADD FOREIGN KEY (role_name)
         REFERENCES role(name);