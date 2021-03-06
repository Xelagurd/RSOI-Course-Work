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

CREATE SCHEMA IF NOT EXISTS scooters;
CREATE TABLE IF NOT EXISTS scooters.scooters
(
    id                 SERIAL PRIMARY KEY,
    scooter_uid        uuid UNIQUE        NOT NULL,
    provider           VARCHAR(80) UNIQUE NOT NULL,
    max_speed          INT,
    price              INT                NOT NULL,
    charge_recovery    INT                NOT NULL,
    charge_consumption INT                NOT NULL
);
INSERT INTO scooters.scooters (scooter_uid, provider, max_speed, price, charge_recovery, charge_consumption)
VALUES ('f21f75ce-4c83-4f5d-8d88-d4df0483e3b1', 'Yandex', 15, 300, 10, 5),
       ('222de3fe-39ff-4f36-8965-3839abd7f3ce', 'Samokat', 10, 230, 20, 10);

CREATE SCHEMA IF NOT EXISTS located_scooters;
CREATE TABLE IF NOT EXISTS located_scooters.located_scooters
(
    id                  SERIAL PRIMARY KEY,
    located_scooter_uid uuid UNIQUE NOT NULL,
    scooter_uid         uuid        NOT NULL,
    rental_station_uid  uuid        NOT NULL,
    registration_number VARCHAR(20) NOT NULL,
    current_charge      INT,
    availability        BOOLEAN     NOT NULL
);
INSERT INTO located_scooters.located_scooters (located_scooter_uid, scooter_uid, rental_station_uid,
                                               registration_number, current_charge, availability)
VALUES ('109b42f3-198d-4c89-9276-a7520a7120ab', 'f21f75ce-4c83-4f5d-8d88-d4df0483e3b1',
        'a5da7b5b-e6ba-4c33-a69d-4dccb07509ee', '150-240', 100, true),
       ('2eb7a884-6939-480f-97af-710cf3904921', '222de3fe-39ff-4f36-8965-3839abd7f3ce',
        '1c5d8e57-a6aa-4a38-a2e1-f7f0bce008f4', '150-240', 80, false);

CREATE TABLE IF NOT EXISTS located_scooters.rental_stations
(
    id                 SERIAL PRIMARY KEY,
    rental_station_uid uuid UNIQUE         NOT NULL,
    location           VARCHAR(160) UNIQUE NOT NULL
);
INSERT INTO located_scooters.rental_stations (rental_station_uid, location)
VALUES ('a5da7b5b-e6ba-4c33-a69d-4dccb07509ee', 'Москва, Бауманская, 1'),
       ('1c5d8e57-a6aa-4a38-a2e1-f7f0bce008f4', 'Москва, Бауманская, 9');

CREATE SCHEMA IF NOT EXISTS rentals;
CREATE TABLE IF NOT EXISTS rentals.rentals
(
    id                  SERIAL PRIMARY KEY,
    rental_uid          uuid UNIQUE              NOT NULL,
    user_uid            uuid                     NOT NULL,
    located_scooter_uid uuid                     NOT NULL,
    payment_uid         uuid                     NOT NULL,
    taken_from          uuid                     NOT NULL,
    return_to           uuid                     NOT NULL,
    date_from           TIMESTAMP                NOT NULL,
    date_to             TIMESTAMP                NOT NULL,
    status              VARCHAR(20)              NOT NULL
        CHECK (status IN ('IN_PROGRESS', 'FINISHED', 'CANCELED'))
);
INSERT INTO rentals.rentals (rental_uid, user_uid, located_scooter_uid, payment_uid, taken_from, return_to, date_from,
                             date_to, status)
VALUES ('121afffa-0bc8-42c5-b9db-fb4e1695d532', 'd24dff82-b64c-4767-a98d-e0b8d7701ce5',
        '109b42f3-198d-4c89-9276-a7520a7120ab', 'd912dad6-0307-4f42-927b-a24bdd7f125a',
        'a5da7b5b-e6ba-4c33-a69d-4dccb07509ee', 'a5da7b5b-e6ba-4c33-a69d-4dccb07509ee', '2021-05-10 18:20:18',
        '2021-05-10 19:20:18', 'FINISHED'),
       ('99307fd1-4f9d-454a-bd2e-5de7eb445133', 'd24dff82-b64c-4767-a98d-e0b8d7701ce5',
        '2eb7a884-6939-480f-97af-710cf3904921', 'a17fb6a8-8ccc-4470-8360-b375ee9930fc',
        '1c5d8e57-a6aa-4a38-a2e1-f7f0bce008f4', '1c5d8e57-a6aa-4a38-a2e1-f7f0bce008f4', '2021-06-10 20:20:18',
        '2021-06-10 21:20:18', 'IN_PROGRESS');

CREATE SCHEMA IF NOT EXISTS payments;
CREATE TABLE IF NOT EXISTS payments.payments
(
    id          SERIAL PRIMARY KEY,
    payment_uid uuid UNIQUE NOT NULL,
    price       INT         NOT NULL,
    status      VARCHAR(20) NOT NULL
        CHECK (status IN ('PAID', 'CANCELED'))
);
INSERT INTO payments.payments (payment_uid, price, status)
VALUES ('d912dad6-0307-4f42-927b-a24bdd7f125a', 300, 'PAID'),
       ('a17fb6a8-8ccc-4470-8360-b375ee9930fc', 230, 'PAID');

CREATE SCHEMA IF NOT EXISTS statistic_operations;
CREATE TABLE IF NOT EXISTS statistic_operations.statistic_operations
(
    id                       SERIAL PRIMARY KEY,
    statistic_operation_uid  uuid UNIQUE              NOT NULL,
    service_type             VARCHAR(20)              NOT NULL
        CHECK (service_type IN ('SESSION', 'SCOOTER', 'STATION', 'RENTAL', 'PAYMENT', 'STATISTIC')),
    statistic_operation_type VARCHAR(20)              NOT NULL
        CHECK (statistic_operation_type IN ('GET', 'GET_ALL', 'CREATE', 'UPDATE', 'REMOVE')),
    date                     TIMESTAMP                NOT NULL,
    user_uid                 uuid                     NOT NULL,
    scooter_uid              uuid,
    located_scooter_uid      uuid,
    rental_station_uid       uuid,
    rental_uid               uuid,
    payment_uid              uuid
);