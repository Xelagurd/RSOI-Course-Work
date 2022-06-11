package com.example.rsoi_course_work.gateway_service.model;

import java.util.Date;
import java.util.Objects;
import java.util.UUID;

public class CreateRentalRequest {
    private UUID scooterUid;
    private Date dateFrom;
    private Date dateTo;

    public CreateRentalRequest() {

    }

    public CreateRentalRequest(UUID scooterUid, Date dateFrom, Date dateTo) {
        this.scooterUid = scooterUid;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
    }

    public static String getString(Date dateFrom) {
        String date = (dateFrom.getYear() + 1900) + "-";
        date += ((dateFrom.getMonth() + 1) > 9 ? (dateFrom.getMonth() + 1) :
                "0" + (dateFrom.getMonth() + 1)) + "-";
        date += dateFrom.getDate() > 9 ? dateFrom.getDate() :
                "0" + dateFrom.getDate();
        return date;
    }

    public UUID getScooterUid() {
        return scooterUid;
    }

    public void setScooterUid(UUID scooterUid) {
        this.scooterUid = scooterUid;
    }

    public Date getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(Date dateFrom) {
        this.dateFrom = dateFrom;
    }

    public String getDateFromString() {
        return getString(dateFrom);
    }

    public Date getDateTo() {
        return dateTo;
    }

    public void setDateTo(Date dateTo) {
        this.dateTo = dateTo;
    }

    public String getDateToString() {
        return getString(dateTo);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CreateRentalRequest)) return false;

        CreateRentalRequest that = (CreateRentalRequest) o;

        if (!Objects.equals(scooterUid, that.scooterUid)) return false;
        if (!Objects.equals(dateFrom, that.dateFrom)) return false;
        return Objects.equals(dateTo, that.dateTo);
    }

    @Override
    public int hashCode() {
        int result = scooterUid != null ? scooterUid.hashCode() : 0;
        result = 31 * result + (dateFrom != null ? dateFrom.hashCode() : 0);
        result = 31 * result + (dateTo != null ? dateTo.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "CreateRentalRequest{" +
                "scooterUid=" + scooterUid +
                ", dateFrom=" + dateFrom +
                ", date_to=" + dateTo +
                '}';
    }
}
