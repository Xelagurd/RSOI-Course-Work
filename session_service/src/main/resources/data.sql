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
INSERT INTO users.users (user_uid, login, password, is_admin)
VALUES ('f5259e18-6164-4880-b973-1781c20a5b26', 'admin', 'admin', 'ADMIN');