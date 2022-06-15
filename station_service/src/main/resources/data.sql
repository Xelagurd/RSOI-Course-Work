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