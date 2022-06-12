package com.example.rsoi_course_work.gateway_service.model.rental_station;

import java.util.Objects;
import java.util.UUID;

public class RentalStationInfo {
    private UUID rental_station_uid;
    private String location;

    public RentalStationInfo() {
    }

    public RentalStationInfo(RentalStation rentalStation) {
        this.rental_station_uid = rentalStation.getRental_station_uid();
        this.location = rentalStation.getLocation();
    }

    public RentalStationInfo(UUID rental_station_uid, String location) {
        this.rental_station_uid = rental_station_uid;
        this.location = location;
    }

    public UUID getRental_station_uid() {
        return rental_station_uid;
    }

    public void setRental_station_uid(UUID rental_station_uid) {
        this.rental_station_uid = rental_station_uid;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RentalStationInfo that = (RentalStationInfo) o;

        if (!Objects.equals(rental_station_uid, that.rental_station_uid))
            return false;
        return Objects.equals(location, that.location);
    }

    @Override
    public int hashCode() {
        int result = rental_station_uid != null ? rental_station_uid.hashCode() : 0;
        result = 31 * result + (location != null ? location.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "RentalStationInfo{" +
                "rental_station_uid=" + rental_station_uid +
                ", location='" + location + '\'' +
                '}';
    }
}
