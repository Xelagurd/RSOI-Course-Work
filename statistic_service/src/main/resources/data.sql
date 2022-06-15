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