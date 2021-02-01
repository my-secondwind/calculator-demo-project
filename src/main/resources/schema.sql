DROP TABLE IF EXISTS operation;

CREATE TABLE operation
(
    id                 LONG AUTO_INCREMENT PRIMARY KEY,
    expression         VARCHAR(250) NOT NULL,
    result             VARCHAR(250) NOT NULL,
    enterDate          DATE       NOT NULL,
    userId             LONG        NOT NULL
);
