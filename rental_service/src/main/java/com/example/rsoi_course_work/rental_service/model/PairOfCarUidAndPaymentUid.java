package com.example.rsoi_course_work.rental_service.model;

import java.util.Objects;
import java.util.UUID;

public class PairOfScooterUidAndPaymentUid {
    private UUID scooterUid;
    private UUID paymentUid;

    public PairOfScooterUidAndPaymentUid() {
    }

    public PairOfScooterUidAndPaymentUid(UUID scooterUid, UUID paymentUid) {
        this.scooterUid = scooterUid;
        this.paymentUid = paymentUid;
    }

    public UUID getScooterUid() {
        return scooterUid;
    }

    public void setScooterUid(UUID scooterUid) {
        this.scooterUid = scooterUid;
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
        if (!(o instanceof PairOfScooterUidAndPaymentUid)) return false;

        PairOfScooterUidAndPaymentUid that = (PairOfScooterUidAndPaymentUid) o;

        if (!Objects.equals(scooterUid, that.scooterUid)) return false;
        return Objects.equals(paymentUid, that.paymentUid);
    }

    @Override
    public int hashCode() {
        int result = scooterUid != null ? scooterUid.hashCode() : 0;
        result = 31 * result + (paymentUid != null ? paymentUid.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "PairOfScooterUidAndPaymentUid{" +
                "scooterUid=" + scooterUid +
                ", paymentUid=" + paymentUid +
                '}';
    }
}
