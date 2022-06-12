package com.example.rsoi_course_work.gateway_service.model.located_scooter;

import java.util.Objects;
import java.util.UUID;

public class CreateLocatedScooterRequest {
    private UUID scooter_uid;
    private UUID rental_station_uid;
    private String registration_number;

    public CreateLocatedScooterRequest() {
    }

    public CreateLocatedScooterRequest(UUID scooter_uid, UUID rental_station_uid, String registration_number) {
        this.scooter_uid = scooter_uid;
        this.rental_station_uid = rental_station_uid;
        this.registration_number = registration_number;
    }

    public UUID getScooter_uid() {
        return scooter_uid;
    }

    public void setScooter_uid(UUID scooter_uid) {
        this.scooter_uid = scooter_uid;
    }

    public UUID getRental_station_uid() {
        return rental_station_uid;
    }

    public void setRental_station_uid(UUID rental_station_uid) {
        this.rental_station_uid = rental_station_uid;
    }

    public String getRegistration_number() {
        return registration_number;
    }

    public void setRegistration_number(String registration_number) {
        this.registration_number = registration_number;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CreateLocatedScooterRequest that = (CreateLocatedScooterRequest) o;

        if (!Objects.equals(scooter_uid, that.scooter_uid)) return false;
        if (!Objects.equals(rental_station_uid, that.rental_station_uid))
            return false;
        return Objects.equals(registration_number, that.registration_number);
    }

    @Override
    public int hashCode() {
        int result = scooter_uid != null ? scooter_uid.hashCode() : 0;
        result = 31 * result + (rental_station_uid != null ? rental_station_uid.hashCode() : 0);
        result = 31 * result + (registration_number != null ? registration_number.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "CreateLocatedScooterRequest{" +
                "scooter_uid=" + scooter_uid +
                ", rental_station_uid=" + rental_station_uid +
                ", registration_number='" + registration_number + '\'' +
                '}';
    }
}
