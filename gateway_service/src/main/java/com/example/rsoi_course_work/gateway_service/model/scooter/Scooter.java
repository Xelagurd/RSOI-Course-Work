package com.example.rsoi_course_work.gateway_service.model.scooter;

import java.util.Objects;
import java.util.UUID;

public class Scooter {
    private Long id;
    private UUID scooter_uid;
    private String provider;
    private Integer max_speed;
    private Integer price;
    private Integer charge_recovery;
    private Integer charge_consumption;

    public Scooter() {
    }

    public Scooter(Long id, UUID scooter_uid, String provider, Integer max_speed, Integer price, Integer charge_recovery, Integer charge_consumption) {
        this.id = id;
        this.scooter_uid = scooter_uid;
        this.provider = provider;
        this.max_speed = max_speed;
        this.price = price;
        this.charge_recovery = charge_recovery;
        this.charge_consumption = charge_consumption;
    }

    public Scooter(UUID scooter_uid, String provider, Integer max_speed, Integer price, Integer charge_recovery, Integer charge_consumption) {
        this.scooter_uid = scooter_uid;
        this.provider = provider;
        this.max_speed = max_speed;
        this.price = price;
        this.charge_recovery = charge_recovery;
        this.charge_consumption = charge_consumption;
    }

    public ScooterInfo getInfo() {
        return new ScooterInfo(scooter_uid, provider, max_speed, price, charge_recovery, charge_consumption);
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

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public Integer getMax_speed() {
        return max_speed;
    }

    public void setMax_speed(Integer max_speed) {
        this.max_speed = max_speed;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getCharge_recovery() {
        return charge_recovery;
    }

    public void setCharge_recovery(Integer charge_recovery) {
        this.charge_recovery = charge_recovery;
    }

    public Integer getCharge_consumption() {
        return charge_consumption;
    }

    public void setCharge_consumption(Integer charge_consumption) {
        this.charge_consumption = charge_consumption;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Scooter scooter = (Scooter) o;

        if (!Objects.equals(id, scooter.id)) return false;
        if (!Objects.equals(scooter_uid, scooter.scooter_uid)) return false;
        if (!Objects.equals(provider, scooter.provider)) return false;
        if (!Objects.equals(max_speed, scooter.max_speed)) return false;
        if (!Objects.equals(price, scooter.price)) return false;
        if (!Objects.equals(charge_recovery, scooter.charge_recovery))
            return false;
        return Objects.equals(charge_consumption, scooter.charge_consumption);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (scooter_uid != null ? scooter_uid.hashCode() : 0);
        result = 31 * result + (provider != null ? provider.hashCode() : 0);
        result = 31 * result + (max_speed != null ? max_speed.hashCode() : 0);
        result = 31 * result + (price != null ? price.hashCode() : 0);
        result = 31 * result + (charge_recovery != null ? charge_recovery.hashCode() : 0);
        result = 31 * result + (charge_consumption != null ? charge_consumption.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Scooter{" +
                "id=" + id +
                ", scooter_uid=" + scooter_uid +
                ", provider='" + provider + '\'' +
                ", max_speed=" + max_speed +
                ", price=" + price +
                ", charge_recovery=" + charge_recovery +
                ", charge_consumption=" + charge_consumption +
                '}';
    }
}
