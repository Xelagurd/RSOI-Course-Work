CREATE SCHEMA IF NOT EXISTS scooters;
CREATE TABLE IF NOT EXISTS scooters.scooters
(
    id                  SERIAL PRIMARY KEY,
    scooter_uid             uuid UNIQUE NOT NULL,
    brand               VARCHAR(80) NOT NULL,
    model               VARCHAR(80) NOT NULL,
    registration_number VARCHAR(20) NOT NULL,
    power               INT,
    price               INT         NOT NULL,
    type                VARCHAR(20)
        CHECK (type IN ('SEDAN', 'SUV', 'MINIVAN', 'ROADSTER')),
    availability        BOOLEAN     NOT NULL
);
INSERT INTO scooters.scooters (scooter_uid, brand, model, registration_number, power, type, price, availability)
VALUES ('109b42f3-198d-4c89-9276-a7520a7120ab', 'Mercedes Benz', 'GLA 250', 'ЛО777Х799', 249, 'SEDAN', 3500, true);
