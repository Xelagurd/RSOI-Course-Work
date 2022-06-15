package com.example.rsoi_course_work.front.model.located_scooter;


import com.example.rsoi_course_work.front.model.rental_station.RentalStationInfo;
import com.example.rsoi_course_work.front.model.scooter.ScooterInfo;

import java.util.Objects;
import java.util.UUID;

public class LocatedScooterInfo {
    private UUID located_scooter_uid;
    private ScooterInfo scooter;
    private RentalStationInfo rental_station;
    private String registration_number;
    private Integer current_charge;
    private Boolean availability;

    public LocatedScooterInfo() {
    }

    public LocatedScooterInfo(UUID located_scooter_uid, ScooterInfo scooter, RentalStationInfo rental_station, String registration_number, Integer current_charge, Boolean availability) {
        this.located_scooter_uid = located_scooter_uid;
        this.scooter = scooter;
        this.rental_station = rental_station;
        this.registration_number = registration_number;
        this.current_charge = current_charge;
        this.availability = availability;
    }

    public UUID getLocated_scooter_uid() {
        return located_scooter_uid;
    }

    public void setLocated_scooter_uid(UUID located_scooter_uid) {
        this.located_scooter_uid = located_scooter_uid;
    }

    public ScooterInfo getScooter() {
        return scooter;
    }

    public void setScooter(ScooterInfo scooter) {
        this.scooter = scooter;
    }

    public RentalStationInfo getRental_station() {
        return rental_station;
    }

    public void setRental_station(RentalStationInfo rental_station) {
        this.rental_station = rental_station;
    }

    public String getRegistration_number() {
        return registration_number;
    }

    public void setRegistration_number(String registration_number) {
        this.registration_number = registration_number;
    }

    public Integer getCurrent_charge() {
        return current_charge;
    }

    public void setCurrent_charge(Integer current_charge) {
        this.current_charge = current_charge;
    }

    public Boolean getAvailability() {
        return availability;
    }

    public void setAvailability(Boolean availability) {
        this.availability = availability;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LocatedScooterInfo that = (LocatedScooterInfo) o;

        if (!Objects.equals(located_scooter_uid, that.located_scooter_uid))
            return false;
        if (!Objects.equals(scooter, that.scooter)) return false;
        if (!Objects.equals(rental_station, that.rental_station))
            return false;
        if (!Objects.equals(registration_number, that.registration_number))
            return false;
        if (!Objects.equals(current_charge, that.current_charge))
            return false;
        return Objects.equals(availability, that.availability);
    }

    @Override
    public int hashCode() {
        int result = located_scooter_uid != null ? located_scooter_uid.hashCode() : 0;
        result = 31 * result + (scooter != null ? scooter.hashCode() : 0);
        result = 31 * result + (rental_station != null ? rental_station.hashCode() : 0);
        result = 31 * result + (registration_number != null ? registration_number.hashCode() : 0);
        result = 31 * result + (current_charge != null ? current_charge.hashCode() : 0);
        result = 31 * result + (availability != null ? availability.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        if (scooter == null && rental_station == null) {
            return "Самокат = null, местоположение = null, регистр. номер = " + registration_number;
        } else if (scooter == null) {
            return "Самокат = null, местоположение = " + rental_station + ", регистр. номер = " + registration_number;
        } else if (rental_station == null) {
            return scooter + ", местоположение = null, регистр. номер = " + registration_number;
        } else {
            return scooter + ", местоположение = " + rental_station + ", регистр. номер = " + registration_number;
        }
    }
}
