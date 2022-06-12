CREATE SCHEMA IF NOT EXISTS scooters;
CREATE TABLE IF NOT EXISTS scooters.scooters
(
    id          SERIAL PRIMARY KEY,
    scooter_uid uuid UNIQUE NOT NULL,
    provider    VARCHAR(80) UNIQUE NOT NULL,
    max_speed   INT,
    price       INT         NOT NULL
);
INSERT INTO scooters.scooters (scooter_uid, provider, max_speed, price)
VALUES ('f21f75ce-4c83-4f5d-8d88-d4df0483e3b1', 'Yandex', 15, 300);
INSERT INTO scooters.scooters (scooter_uid, provider, max_speed, price)
VALUES ('222de3fe-39ff-4f36-8965-3839abd7f3ce', 'Samokat', 10, 230);
