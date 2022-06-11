package com.example.rsoi_course_work.gateway_service.model;

import java.util.Objects;
import java.util.UUID;

public class CreateRentalResponse {
    private UUID rentalUid;
    private UUID scooterUid;
    private String dateFrom;
    private String dateTo;
    private PaymentInfo payment;
    private RentalStatus status;

    public CreateRentalResponse() {
    }

    public CreateRentalResponse(UUID rentalUid, UUID scooterUid, String dateFrom, String dateTo, PaymentInfo payment, RentalStatus status) {
        this.rentalUid = rentalUid;
        this.scooterUid = scooterUid;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.payment = payment;
        this.status = status;
    }

    public UUID getRentalUid() {
        return rentalUid;
    }

    public void setRentalUid(UUID rentalUid) {
        this.rentalUid = rentalUid;
    }

    public UUID getScooterUid() {
        return scooterUid;
    }

    public void setScooterUid(UUID scooterUid) {
        this.scooterUid = scooterUid;
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

    public PaymentInfo getPayment() {
        return payment;
    }

    public void setPayment(PaymentInfo payment) {
        this.payment = payment;
    }

    public RentalStatus getStatus() {
        return status;
    }

    public void setStatus(RentalStatus status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CreateRentalResponse)) return false;

        CreateRentalResponse that = (CreateRentalResponse) o;

        if (!Objects.equals(rentalUid, that.rentalUid)) return false;
        if (!Objects.equals(scooterUid, that.scooterUid)) return false;
        if (!Objects.equals(dateFrom, that.dateFrom)) return false;
        if (!Objects.equals(dateTo, that.dateTo)) return false;
        if (!Objects.equals(payment, that.payment)) return false;
        return status == that.status;
    }

    @Override
    public int hashCode() {
        int result = rentalUid != null ? rentalUid.hashCode() : 0;
        result = 31 * result + (scooterUid != null ? scooterUid.hashCode() : 0);
        result = 31 * result + (dateFrom != null ? dateFrom.hashCode() : 0);
        result = 31 * result + (dateTo != null ? dateTo.hashCode() : 0);
        result = 31 * result + (payment != null ? payment.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "CreateRentalResponse{" +
                "rental_uid=" + rentalUid +
                ", scooter_uid=" + scooterUid +
                ", date_from=" + dateFrom +
                ", date_to=" + dateTo +
                ", payment=" + payment +
                ", status=" + status +
                '}';
    }
}
