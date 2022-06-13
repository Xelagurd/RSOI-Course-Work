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
    date_from           TIMESTAMP WITH TIME ZONE NOT NULL,
    date_to             TIMESTAMP WITH TIME ZONE NOT NULL,
    status              VARCHAR(20)              NOT NULL
        CHECK (status IN ('IN_PROGRESS', 'FINISHED', 'CANCELED'))
);