package com.example.rsoi_course_work.gateway_service.model.rental;

import java.util.Date;
import java.util.Objects;
import java.util.UUID;

public class CreateRentalRequest {
    private UUID located_scooter_uid;
    private UUID return_to;
    private Date date_from;
    private Date date_to;

    public CreateRentalRequest() {
    }

    public CreateRentalRequest(UUID located_scooter_uid, UUID return_to, Date date_from, Date date_to) {
        this.located_scooter_uid = located_scooter_uid;
        this.return_to = return_to;
        this.date_from = date_from;
        this.date_to = date_to;
    }

    public boolean isValid() {
        return located_scooter_uid != null && return_to != null && date_from != null && date_to != null;
    }

    public UUID getLocated_scooter_uid() {
        return located_scooter_uid;
    }

    public void setLocated_scooter_uid(UUID located_scooter_uid) {
        this.located_scooter_uid = located_scooter_uid;
    }

    public UUID getReturn_to() {
        return return_to;
    }

    public void setReturn_to(UUID return_to) {
        this.return_to = return_to;
    }

    public Date getDate_from() {
        return date_from;
    }

    public void setDate_from(Date date_from) {
        this.date_from = date_from;
    }

    public Date getDate_to() {
        return date_to;
    }

    public void setDate_to(Date date_to) {
        this.date_to = date_to;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CreateRentalRequest that = (CreateRentalRequest) o;

        if (!Objects.equals(located_scooter_uid, that.located_scooter_uid))
            return false;
        if (!Objects.equals(return_to, that.return_to)) return false;
        if (!Objects.equals(date_from, that.date_from)) return false;
        return Objects.equals(date_to, that.date_to);
    }

    @Override
    public int hashCode() {
        int result = located_scooter_uid != null ? located_scooter_uid.hashCode() : 0;
        result = 31 * result + (return_to != null ? return_to.hashCode() : 0);
        result = 31 * result + (date_from != null ? date_from.hashCode() : 0);
        result = 31 * result + (date_to != null ? date_to.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "CreateRentalRequest{" +
                "located_scooter_uid=" + located_scooter_uid +
                ", return_to=" + return_to +
                ", date_from=" + date_from +
                ", date_to=" + date_to +
                '}';
    }
}
