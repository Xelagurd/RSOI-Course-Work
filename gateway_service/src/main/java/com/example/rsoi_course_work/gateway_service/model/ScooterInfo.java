package com.example.rsoi_course_work.gateway_service.model;

import java.util.Objects;
import java.util.UUID;

public class ScooterInfo {
    private UUID scooterUid;
    private String brand;
    private String model;
    private String registrationNumber;

    public ScooterInfo() {
    }

    public ScooterInfo(UUID scooterUid, String brand, String model, String registrationNumber) {
        this.scooterUid = scooterUid;
        this.brand = brand;
        this.model = model;
        this.registrationNumber = registrationNumber;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ScooterInfo)) return false;

        ScooterInfo scooterInfo = (ScooterInfo) o;

        if (!Objects.equals(scooterUid, scooterInfo.scooterUid)) return false;
        if (!Objects.equals(brand, scooterInfo.brand)) return false;
        if (!Objects.equals(model, scooterInfo.model)) return false;
        return Objects.equals(registrationNumber, scooterInfo.registrationNumber);
    }

    @Override
    public int hashCode() {
        int result = scooterUid != null ? scooterUid.hashCode() : 0;
        result = 31 * result + (brand != null ? brand.hashCode() : 0);
        result = 31 * result + (model != null ? model.hashCode() : 0);
        result = 31 * result + (registrationNumber != null ? registrationNumber.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ScooterInfo{" +
                "scooterUid=" + scooterUid +
                ", brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                ", registrationNumber='" + registrationNumber + '\'' +
                '}';
    }
}
