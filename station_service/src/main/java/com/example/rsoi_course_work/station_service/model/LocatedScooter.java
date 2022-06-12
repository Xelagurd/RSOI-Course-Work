package com.example.rsoi_course_work.station_service.model;

import javax.persistence.*;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "located_scooters", schema = "located_scooters")
public class LocatedScooter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "serial")
    private Long id;

    @Column(nullable = false, unique = true)
    private UUID located_scooter_uid;

    @Column(nullable = false)
    private UUID scooter_uid;

    @Column(nullable = false)
    private UUID rental_station_uid;

    @Column(nullable = false, length = 20)
    private String registration_number;

    private Integer current_charge;

    @Column(nullable = false)
    private Boolean availability;

    public LocatedScooter() {
    }

    public LocatedScooter(Long id, UUID located_scooter_uid, UUID scooter_uid, UUID rental_station_uid, String registration_number, Integer current_charge, Boolean availability) {
        this.id = id;
        this.located_scooter_uid = located_scooter_uid;
        this.scooter_uid = scooter_uid;
        this.rental_station_uid = rental_station_uid;
        this.registration_number = registration_number;
        this.current_charge = current_charge;
        this.availability = availability;
    }

    public LocatedScooter(UUID located_scooter_uid, UUID scooter_uid, UUID rental_station_uid, String registration_number, Integer current_charge, Boolean availability) {
        this.located_scooter_uid = located_scooter_uid;
        this.scooter_uid = scooter_uid;
        this.rental_station_uid = rental_station_uid;
        this.registration_number = registration_number;
        this.current_charge = current_charge;
        this.availability = availability;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UUID getLocated_scooter_uid() {
        return located_scooter_uid;
    }

    public void setLocated_scooter_uid(UUID located_scooter_uid) {
        this.located_scooter_uid = located_scooter_uid;
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

        LocatedScooter that = (LocatedScooter) o;

        if (!Objects.equals(id, that.id)) return false;
        if (!Objects.equals(located_scooter_uid, that.located_scooter_uid))
            return false;
        if (!Objects.equals(scooter_uid, that.scooter_uid)) return false;
        if (!Objects.equals(rental_station_uid, that.rental_station_uid))
            return false;
        if (!Objects.equals(registration_number, that.registration_number))
            return false;
        if (!Objects.equals(current_charge, that.current_charge))
            return false;
        return Objects.equals(availability, that.availability);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (located_scooter_uid != null ? located_scooter_uid.hashCode() : 0);
        result = 31 * result + (scooter_uid != null ? scooter_uid.hashCode() : 0);
        result = 31 * result + (rental_station_uid != null ? rental_station_uid.hashCode() : 0);
        result = 31 * result + (registration_number != null ? registration_number.hashCode() : 0);
        result = 31 * result + (current_charge != null ? current_charge.hashCode() : 0);
        result = 31 * result + (availability != null ? availability.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "LocatedScooter{" +
                "id=" + id +
                ", located_scooter_uid=" + located_scooter_uid +
                ", scooter_uid=" + scooter_uid +
                ", rental_station_uid=" + rental_station_uid +
                ", registration_number='" + registration_number + '\'' +
                ", current_charge=" + current_charge +
                ", availability=" + availability +
                '}';
    }
}
