CREATE SCHEMA IF NOT EXISTS payments;
CREATE TABLE IF NOT EXISTS payments.payments
(
    id          SERIAL PRIMARY KEY,
    payment_uid uuid UNIQUE NOT NULL,
    price       INT         NOT NULL,
    status      VARCHAR(20) NOT NULL
        CHECK (status IN ('PAID', 'CANCELED'))
);