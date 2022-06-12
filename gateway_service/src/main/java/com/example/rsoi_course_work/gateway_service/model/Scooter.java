package com.example.rsoi_course_work.gateway_service.model;

import java.util.Objects;
import java.util.UUID;

public class Scooter {
    private Long id;
    private UUID scooter_uid;
    private String provider;
    private Integer max_speed;
    private Integer price;

    public Scooter() {
    }

    public Scooter(Long id, UUID scooter_uid, String provider, Integer max_speed, Integer price) {
        this.id = id;
        this.scooter_uid = scooter_uid;
        this.provider = provider;
        this.max_speed = max_speed;
        this.price = price;
    }

    public Scooter(UUID scooter_uid, String provider, Integer max_speed, Integer price) {
        this.scooter_uid = scooter_uid;
        this.provider = provider;
        this.max_speed = max_speed;
        this.price = price;
    }

    public ScooterInfo getInfo() {
        return new ScooterInfo(scooter_uid, provider, max_speed, price);
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Scooter scooter = (Scooter) o;

        if (!Objects.equals(id, scooter.id)) return false;
        if (!Objects.equals(scooter_uid, scooter.scooter_uid)) return false;
        if (!Objects.equals(provider, scooter.provider)) return false;
        if (!Objects.equals(max_speed, scooter.max_speed)) return false;
        return Objects.equals(price, scooter.price);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (scooter_uid != null ? scooter_uid.hashCode() : 0);
        result = 31 * result + (provider != null ? provider.hashCode() : 0);
        result = 31 * result + (max_speed != null ? max_speed.hashCode() : 0);
        result = 31 * result + (price != null ? price.hashCode() : 0);
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
                '}';
    }
}
