CREATE SCHEMA IF NOT EXISTS users;
CREATE TABLE IF NOT EXISTS users.users
(
    id       SERIAL PRIMARY KEY,
    user_uid uuid UNIQUE NOT NULL,
    login    VARCHAR(32) NOT NULL,
    password VARCHAR(16) NOT NULL,
    is_admin BOOLEAN     NOT NULL
);
INSERT INTO users.users (user_uid, login, password, is_admin)
VALUES ('d24dff82-b64c-4767-a98d-e0b8d7701ce5', 'user', 'user', false);
INSERT INTO users.users (user_uid, login, password, is_admin)
VALUES ('f5259e18-6164-4880-b973-1781c20a5b26', 'admin', 'admin', true);

CREATE SCHEMA IF NOT EXISTS scooters;
CREATE TABLE IF NOT EXISTS scooters.scooters
(
    id          SERIAL PRIMARY KEY,
    scooter_uid uuid UNIQUE NOT NULL,
    provider    VARCHAR(80) NOT NULL,
    max_speed   INT,
    price       INT         NOT NULL
);
INSERT INTO scooters.scooters (scooter_uid, provider, max_speed, price)
VALUES ('f21f75ce-4c83-4f5d-8d88-d4df0483e3b1', 'Yandex', 15, 300);
INSERT INTO scooters.scooters (scooter_uid, provider, max_speed, price)
VALUES ('222de3fe-39ff-4f36-8965-3839abd7f3ce', 'Samokat', 10, 230);

CREATE SCHEMA IF NOT EXISTS located_scooters;
CREATE TABLE IF NOT EXISTS located_scooters.available_scooters
(
    id                    SERIAL PRIMARY KEY,
    available_scooter_uid uuid UNIQUE NOT NULL,
    scooter_uid           uuid        NOT NULL,
    rental_station_uid    uuid        NOT NULL,
    registration_number   VARCHAR(20) NOT NULL,
    current_charge        INT,
    availability          BOOLEAN     NOT NULL
);
INSERT INTO located_scooters.available_scooters (available_scooter_uid, scooter_uid, rental_station_uid,
                                                 registration_number, current_charge, availability)
VALUES ('109b42f3-198d-4c89-9276-a7520a7120ab', 'f21f75ce-4c83-4f5d-8d88-d4df0483e3b1',
        'a5da7b5b-e6ba-4c33-a69d-4dccb07509ee', '150-240', 100, true);
INSERT INTO located_scooters.available_scooters (available_scooter_uid, scooter_uid, rental_station_uid,
                                                 registration_number, current_charge, availability)
VALUES ('2eb7a884-6939-480f-97af-710cf3904921', '222de3fe-39ff-4f36-8965-3839abd7f3ce',
        '1c5d8e57-a6aa-4a38-a2e1-f7f0bce008f4', '150-240', 80, false);

CREATE TABLE IF NOT EXISTS located_scooters.rental_stations
(
    id                 SERIAL PRIMARY KEY,
    rental_station_uid uuid UNIQUE  NOT NULL,
    location           VARCHAR(160) NOT NULL
);
INSERT INTO located_scooters.rental_stations (rental_station_uid, location)
VALUES ('a5da7b5b-e6ba-4c33-a69d-4dccb07509ee', 'Москва, Бауманская, 1');
INSERT INTO located_scooters.rental_stations (rental_station_uid, location)
VALUES ('1c5d8e57-a6aa-4a38-a2e1-f7f0bce008f4', 'Москва, Бауманская, 9');

CREATE SCHEMA IF NOT EXISTS rentals;
CREATE TABLE IF NOT EXISTS rentals.rentals
(
    id                    SERIAL PRIMARY KEY,
    rental_uid            uuid UNIQUE              NOT NULL,
    user_uid              uuid                     NOT NULL,
    available_scooter_uid uuid                     NOT NULL,
    payment_uid           uuid                     NOT NULL,
    taken_from            uuid                     NOT NULL,
    return_to             uuid                     NOT NULL,
    date_from             TIMESTAMP WITH TIME ZONE NOT NULL,
    date_to               TIMESTAMP WITH TIME ZONE NOT NULL,
    status                VARCHAR(20)              NOT NULL
        CHECK (status IN ('IN_PROGRESS', 'FINISHED', 'CANCELED'))
);
INSERT INTO rentals.rentals (rental_uid, user_uid, available_scooter_uid, payment_uid, taken_from, return_to, date_from,
                             date_to, status)
VALUES ('121afffa-0bc8-42c5-b9db-fb4e1695d532', 'd24dff82-b64c-4767-a98d-e0b8d7701ce5',
        '109b42f3-198d-4c89-9276-a7520a7120ab', 'd912dad6-0307-4f42-927b-a24bdd7f125a',
        'a5da7b5b-e6ba-4c33-a69d-4dccb07509ee', 'a5da7b5b-e6ba-4c33-a69d-4dccb07509ee', '2021-05-10 18:20:18.674',
        '2021-05-10 19:20:18.674', 'FINISHED');
INSERT INTO rentals.rentals (rental_uid, user_uid, available_scooter_uid, payment_uid, taken_from, return_to, date_from,
                             date_to, status)
VALUES ('99307fd1-4f9d-454a-bd2e-5de7eb445133', 'd24dff82-b64c-4767-a98d-e0b8d7701ce5',
        '2eb7a884-6939-480f-97af-710cf3904921', 'a17fb6a8-8ccc-4470-8360-b375ee9930fc',
        '1c5d8e57-a6aa-4a38-a2e1-f7f0bce008f4', '1c5d8e57-a6aa-4a38-a2e1-f7f0bce008f4', '2021-06-10 20:20:18.674',
        '2021-06-10 21:20:18.674', 'IN_PROGRESS');

CREATE SCHEMA IF NOT EXISTS payments;
CREATE TABLE IF NOT EXISTS payments.payments
(
    id          SERIAL PRIMARY KEY,
    payment_uid uuid        NOT NULL,
    price       INT         NOT NULL,
    status      VARCHAR(20) NOT NULL
        CHECK (status IN ('PAID', 'CANCELED'))
);
INSERT INTO payments.payments (payment_uid, price, status)
VALUES ('d912dad6-0307-4f42-927b-a24bdd7f125a', 300, 'PAID');
INSERT INTO payments.payments (payment_uid, price, status)
VALUES ('a17fb6a8-8ccc-4470-8360-b375ee9930fc', 230, 'PAID');