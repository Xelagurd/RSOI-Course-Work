package com.example.rsoi_course_work.gateway_service.model;

import java.util.Objects;
import java.util.UUID;

public class RentalResponse {
    private UUID rentalUid;
    private String dateFrom;
    private String dateTo;
    private RentalStatus status;
    private ScooterInfo scooter;
    private PaymentInfo payment;

    public RentalResponse() {
    }

    public RentalResponse(UUID rentalUid, String dateFrom, String dateTo, RentalStatus status, ScooterInfo scooter, PaymentInfo payment) {
        this.rentalUid = rentalUid;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.status = status;
        this.scooter = scooter;
        this.payment = payment;
    }

    public UUID getRentalUid() {
        return rentalUid;
    }

    public void setRentalUid(UUID rentalUid) {
        this.rentalUid = rentalUid;
    }

    public String getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(String dateFrom) {
        this.dateFrom = dateFrom;
    }

    public String getDateTo() {
        return dateTo;
    }

    public void setDateTo(String dateTo) {
        this.dateTo = dateTo;
    }

    public RentalStatus getStatus() {
        return status;
    }

    public void setStatus(RentalStatus status) {
        this.status = status;
    }

    public ScooterInfo getScooter() {
        return scooter;
    }

    public void setScooter(ScooterInfo scooter) {
        this.scooter = scooter;
    }

    public PaymentInfo getPayment() {
        return payment;
    }

    public void setPayment(PaymentInfo payment) {
        this.payment = payment;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RentalResponse)) return false;

        RentalResponse that = (RentalResponse) o;

        if (!Objects.equals(rentalUid, that.rentalUid)) return false;
        if (!Objects.equals(dateFrom, that.dateFrom)) return false;
        if (!Objects.equals(dateTo, that.dateTo)) return false;
        if (status != that.status) return false;
        if (!Objects.equals(scooter, that.scooter)) return false;
        return Objects.equals(payment, that.payment);
    }

    @Override
    public int hashCode() {
        int result = rentalUid != null ? rentalUid.hashCode() : 0;
        result = 31 * result + (dateFrom != null ? dateFrom.hashCode() : 0);
        result = 31 * result + (dateTo != null ? dateTo.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (scooter != null ? scooter.hashCode() : 0);
        result = 31 * result + (payment != null ? payment.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "RentalResponse{" +
                "rentalUid=" + rentalUid +
                ", dateFrom=" + dateFrom +
                ", dateTo=" + dateTo +
                ", status=" + status +
                ", scooter=" + scooter +
                ", payment=" + payment +
                '}';
    }
}
