package com.example.rsoi_course_work.gateway_service.model;

import java.util.Objects;
import java.util.UUID;

public class ScooterResponse {
    private UUID scooterUid;
    private String brand;
    private String model;
    private String registrationNumber;
    private Integer power;
    private Integer price;
    private ScooterType type;
    private Boolean available;

    public ScooterResponse() {
    }

    public ScooterResponse(UUID scooterUid, String brand, String model, String registrationNumber, Integer power, Integer price, ScooterType type, Boolean available) {
        this.scooterUid = scooterUid;
        this.brand = brand;
        this.model = model;
        this.registrationNumber = registrationNumber;
        this.power = power;
        this.price = price;
        this.type = type;
        this.available = available;
    }

    public UUID getScooterUid() {
        return scooterUid;
    }

    public void setScooterUid(UUID scooterUid) {
        this.scooterUid = scooterUid;
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

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
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

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ScooterResponse)) return false;

        ScooterResponse that = (ScooterResponse) o;

        if (!Objects.equals(scooterUid, that.scooterUid)) return false;
        if (!Objects.equals(brand, that.brand)) return false;
        if (!Objects.equals(model, that.model)) return false;
        if (!Objects.equals(registrationNumber, that.registrationNumber))
            return false;
        if (!Objects.equals(power, that.power)) return false;
        if (!Objects.equals(price, that.price)) return false;
        if (type != that.type) return false;
        return Objects.equals(available, that.available);
    }

    @Override
    public int hashCode() {
        int result = scooterUid != null ? scooterUid.hashCode() : 0;
        result = 31 * result + (brand != null ? brand.hashCode() : 0);
        result = 31 * result + (model != null ? model.hashCode() : 0);
        result = 31 * result + (registrationNumber != null ? registrationNumber.hashCode() : 0);
        result = 31 * result + (power != null ? power.hashCode() : 0);
        result = 31 * result + (price != null ? price.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (available != null ? available.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ScooterResponse{" +
                "scooterUid=" + scooterUid +
                ", brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                ", registrationNumber='" + registrationNumber + '\'' +
                ", power=" + power +
                ", price=" + price +
                ", type=" + type +
                ", availability=" + available +
                '}';
    }
}
