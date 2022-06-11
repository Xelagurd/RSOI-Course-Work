package com.example.rsoi_course_work.scooter_service.model;

import java.util.List;
import java.util.Objects;

public class ScooterPartialList {
    private Integer totalElements;
    private List<Scooter> scooters;

    public ScooterPartialList() {
    }

    public ScooterPartialList(Integer totalElements, List<Scooter> scooters) {
        this.totalElements = totalElements;
        this.scooters = scooters;
    }

    public Integer getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(Integer totalElements) {
        this.totalElements = totalElements;
    }

    public List<Scooter> getScooters() {
        return scooters;
    }

    public void setScooters(List<Scooter> scooters) {
        this.scooters = scooters;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ScooterPartialList)) return false;

        ScooterPartialList that = (ScooterPartialList) o;

        if (!Objects.equals(totalElements, that.totalElements))
            return false;
        return Objects.equals(scooters, that.scooters);
    }

    @Override
    public int hashCode() {
        int result = totalElements != null ? totalElements.hashCode() : 0;
        result = 31 * result + (scooters != null ? scooters.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ScooterPartialList{" +
                "totalElements=" + totalElements +
                ", scooters=" + scooters +
                '}';
    }
}
