package com.example.rsoi_course_work.rental_service.model;

import java.util.Objects;
import java.util.UUID;

public class FinishedRentalResponse {
    private UUID locatedScooterUid;
    private UUID returnToRentalStationUid;

    public FinishedRentalResponse() {
    }

    public FinishedRentalResponse(UUID locatedScooterUid, UUID returnToRentalStationUid) {
        this.locatedScooterUid = locatedScooterUid;
        this.returnToRentalStationUid = returnToRentalStationUid;
    }

    public UUID getLocatedScooterUid() {
        return locatedScooterUid;
    }

    public void setLocatedScooterUid(UUID locatedScooterUid) {
        this.locatedScooterUid = locatedScooterUid;
    }

    public UUID getReturnToRentalStationUid() {
        return returnToRentalStationUid;
    }

    public void setReturnToRentalStationUid(UUID returnToRentalStationUid) {
        this.returnToRentalStationUid = returnToRentalStationUid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FinishedRentalResponse that = (FinishedRentalResponse) o;

        if (!Objects.equals(locatedScooterUid, that.locatedScooterUid))
            return false;
        return Objects.equals(returnToRentalStationUid, that.returnToRentalStationUid);
    }

    @Override
    public int hashCode() {
        int result = locatedScooterUid != null ? locatedScooterUid.hashCode() : 0;
        result = 31 * result + (returnToRentalStationUid != null ? returnToRentalStationUid.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "FinishedRentalResponse{" +
                "locatedScooterUid=" + locatedScooterUid +
                ", returnToRentalStationUid=" + returnToRentalStationUid +
                '}';
    }
}

