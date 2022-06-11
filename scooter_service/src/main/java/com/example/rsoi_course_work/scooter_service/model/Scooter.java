package com.example.rsoi_course_work.scooter_service.model;

import javax.persistence.*;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "scooters", schema = "scooters")
public class Scooter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "serial")
    private Long id;

    @Column(nullable = false, unique = true)
    private UUID scooter_uid;

    @Column(nullable = false, length = 80)
    private String brand;

    @Column(nullable = false, length = 80)
    private String model;

    @Column(nullable = false, length = 20)
    private String registration_number;

    private Integer power;

    @Column(nullable = false)
    private Integer price;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private ScooterType type;

    @Column(nullable = false)
    private Boolean availability;

    public Scooter() {
    }

    public Scooter(Long id, UUID scooter_uid, String brand, String model, String registration_number, Integer power, Integer price, ScooterType type, Boolean availability) {
        super();
        this.id = id;
        this.scooter_uid = scooter_uid;
        this.brand = brand;
        this.model = model;
        this.registration_number = registration_number;
        this.power = power;
        this.price = price;
        this.type = type;
        this.availability = availability;
    }

    public Scooter(UUID scooter_uid, String brand, String model, String registration_number, Integer power, Integer price, ScooterType type, Boolean availability) {
        super();
        this.scooter_uid = scooter_uid;
        this.brand = brand;
        this.model = model;
        this.registration_number = registration_number;
        this.power = power;
        this.price = price;
        this.type = type;
        this.availability = availability;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UUID getScooter_uid() {
        return scooter_uid;
    }

    public void setScooter_uid(UUID scooter_uid) {
        this.scooter_uid = scooter_uid;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getRegistration_number() {
        return registration_number;
    }

    public void setRegistration_number(String registration_number) {
        this.registration_number = registration_number;
    }

    public Integer getPower() {
        return power;
    }

    public void setPower(Integer power) {
        this.power = power;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public ScooterType getType() {
        return type;
    }

    public void setType(ScooterType type) {
        this.type = type;
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
        if (!(o instanceof Scooter)) return false;

        Scooter scooter = (Scooter) o;

        if (!Objects.equals(id, scooter.id)) return false;
        if (!Objects.equals(scooter_uid, scooter.scooter_uid)) return false;
        if (!Objects.equals(brand, scooter.brand)) return false;
        if (!Objects.equals(model, scooter.model)) return false;
        if (!Objects.equals(registration_number, scooter.registration_number)) return false;
        if (!Objects.equals(power, scooter.power)) return false;
        if (!Objects.equals(price, scooter.price)) return false;
        if (type != scooter.type) return false;
        return Objects.equals(availability, scooter.availability);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (scooter_uid != null ? scooter_uid.hashCode() : 0);
        result = 31 * result + (brand != null ? brand.hashCode() : 0);
        result = 31 * result + (model != null ? model.hashCode() : 0);
        result = 31 * result + (registration_number != null ? registration_number.hashCode() : 0);
        result = 31 * result + (power != null ? power.hashCode() : 0);
        result = 31 * result + (price != null ? price.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (availability != null ? availability.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Scooter{" +
                "id=" + id +
                ", scooter_uid=" + scooter_uid +
                ", brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                ", registration_number='" + registration_number + '\'' +
                ", power=" + power +
                ", price=" + price +
                ", type=" + type +
                ", availability=" + availability +
                '}';
    }
}
