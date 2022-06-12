package com.example.rsoi_course_work.gateway_service.model.rental_station;

import java.util.Objects;

public class CreateRentalStationRequest {
    private String location;

    public CreateRentalStationRequest() {
    }

    public CreateRentalStationRequest(String location) {
        this.location = location;
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

        CreateRentalStationRequest that = (CreateRentalStationRequest) o;

        return Objects.equals(location, that.location);
    }

    @Override
    public int hashCode() {
        return location != null ? location.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "CreateRentalStationRequest{" +
                "location='" + location + '\'' +
                '}';
    }
}

