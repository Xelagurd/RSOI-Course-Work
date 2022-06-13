package com.example.rsoi_course_work.front.model.rental;

import java.util.Objects;
import java.util.UUID;

public class CanceledRentalResponse {
    private UUID locatedScooterUid;
    private UUID paymentUid;

    public CanceledRentalResponse() {
    }

    public CanceledRentalResponse(UUID locatedScooterUid, UUID paymentUid) {
        this.locatedScooterUid = locatedScooterUid;
        this.paymentUid = paymentUid;
    }

    public UUID getLocatedScooterUid() {
        return locatedScooterUid;
    }

    public void setLocatedScooterUid(UUID locatedScooterUid) {
        this.locatedScooterUid = locatedScooterUid;
    }

    public UUID getPaymentUid() {
        return paymentUid;
    }

    public void setPaymentUid(UUID paymentUid) {
        this.paymentUid = paymentUid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CanceledRentalResponse)) return false;

        CanceledRentalResponse that = (CanceledRentalResponse) o;

        if (!Objects.equals(locatedScooterUid, that.locatedScooterUid)) return false;
        return Objects.equals(paymentUid, that.paymentUid);
    }

    @Override
    public int hashCode() {
        int result = locatedScooterUid != null ? locatedScooterUid.hashCode() : 0;
        result = 31 * result + (paymentUid != null ? paymentUid.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "CanceledRentalResponse{" +
                "locatedScooterUid=" + locatedScooterUid +
                ", paymentUid=" + paymentUid +
                '}';
    }
}
