CREATE SCHEMA IF NOT EXISTS users;
CREATE TABLE IF NOT EXISTS users.users
(
    id       SERIAL PRIMARY KEY,
    user_uid uuid UNIQUE NOT NULL,
    name     VARCHAR(80) NOT NULL,
    surname  VARCHAR(80) NOT NULL,
    login    VARCHAR(32) NOT NULL,
    password VARCHAR(80) NOT NULL,
    role     VARCHAR(20) NOT NULL
        CHECK (role IN ('USER', 'ADMIN'))
);
INSERT INTO users.users (user_uid, name, surname, login, password, role)
VALUES ('d24dff82-b64c-4767-a98d-e0b8d7701ce5', 'Алекс2', 'Друг2', 'user',
        '$2a$12$P9zK7d.jJHapfXwsHj2n/OQfHxGCObx65jVgf5my3eqAPdbypNLkW', 'USER'),
       ('f5259e18-6164-4880-b973-1781c20a5b26', 'Алекс', 'Друг', 'admin',
        '$2a$12$maRMXe52vZW5eARbBi5fu.CMa/.pa1jUBf8IMwH/0Si1tFu/GK/dm', 'ADMIN');
