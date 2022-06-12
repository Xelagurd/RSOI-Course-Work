package com.example.rsoi_course_work.station_service.model;

import java.util.List;
import java.util.Objects;

public class LocatedScooterPartialList {
    private Integer totalElements;
    private List<LocatedScooter> locatedScooters;

    public LocatedScooterPartialList() {
    }

    public LocatedScooterPartialList(Integer totalElements, List<LocatedScooter> locatedScooters) {
        this.totalElements = totalElements;
        this.locatedScooters = locatedScooters;
    }

    public Integer getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(Integer totalElements) {
        this.totalElements = totalElements;
    }

    public List<LocatedScooter> getLocatedScooters() {
        return locatedScooters;
    }

    public void setLocatedScooters(List<LocatedScooter> locatedScooters) {
        this.locatedScooters = locatedScooters;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LocatedScooterPartialList)) return false;

        LocatedScooterPartialList that = (LocatedScooterPartialList) o;

        if (!Objects.equals(totalElements, that.totalElements))
            return false;
        return Objects.equals(locatedScooters, that.locatedScooters);
    }

    @Override
    public int hashCode() {
        int result = totalElements != null ? totalElements.hashCode() : 0;
        result = 31 * result + (locatedScooters != null ? locatedScooters.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "LocatedLocatedScooterPartialList{" +
                "totalElements=" + totalElements +
                ", locatedScooters=" + locatedScooters +
                '}';
    }
}
