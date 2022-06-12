CREATE SCHEMA IF NOT EXISTS users;
CREATE TABLE IF NOT EXISTS users.users
(
    id       SERIAL PRIMARY KEY,
    user_uid uuid UNIQUE NOT NULL,
    name     VARCHAR(80) NOT NULL,
    surname  VARCHAR(80) NOT NULL,
    login    VARCHAR(32) NOT NULL,
    password VARCHAR(16) NOT NULL,
    role     VARCHAR(20) NOT NULL
        CHECK (role IN ('USER', 'ADMIN'))
);
INSERT INTO users.users (user_uid, name, surname, login, password, role)
VALUES ('d24dff82-b64c-4767-a98d-e0b8d7701ce5', 'Алекс2', 'Друг2', 'user', 'user', 'USER');
INSERT INTO users.users (user_uid, name, surname, login, password, role)
VALUES ('f5259e18-6164-4880-b973-1781c20a5b26', 'Алекс', 'Друг', 'admin', 'admin', 'ADMIN');