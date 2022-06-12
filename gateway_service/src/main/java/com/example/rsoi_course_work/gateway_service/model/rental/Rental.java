package com.example.rsoi_course_work.gateway_service.model.rental;

import java.util.Date;
import java.util.Objects;
import java.util.UUID;

public class Rental {
    private Long id;
    private UUID rental_uid;
    private UUID user_uid;
    private UUID located_scooter_uid;
    private UUID payment_uid;
    private UUID taken_from;
    private UUID return_to;
    private Date date_from;
    private Date date_to;
    private RentalStatus status;

    public Rental() {
    }

    public Rental(Long id, UUID rental_uid, UUID user_uid, UUID located_scooter_uid, UUID payment_uid, UUID taken_from, UUID return_to, Date date_from, Date date_to, RentalStatus status) {
        this.id = id;
        this.rental_uid = rental_uid;
        this.user_uid = user_uid;
        this.located_scooter_uid = located_scooter_uid;
        this.payment_uid = payment_uid;
        this.taken_from = taken_from;
        this.return_to = return_to;
        this.date_from = date_from;
        this.date_to = date_to;
        this.status = status;
    }

    public Rental(UUID rental_uid, UUID user_uid, UUID located_scooter_uid, UUID payment_uid, UUID taken_from, UUID return_to, Date date_from, Date date_to, RentalStatus status) {
        this.rental_uid = rental_uid;
        this.user_uid = user_uid;
        this.located_scooter_uid = located_scooter_uid;
        this.payment_uid = payment_uid;
        this.taken_from = taken_from;
        this.return_to = return_to;
        this.date_from = date_from;
        this.date_to = date_to;
        this.status = status;
    }

    public RentalInfo getInfo() {
        return new RentalInfo(rental_uid, null, null, null, null,
                null, getDate_from_string(), getDate_to_string(), status);
    }

    public static String getString(Date date_from) {
        String date = (date_from.getYear() + 1900) + "-";
        date += ((date_from.getMonth() + 1) > 9 ? (date_from.getMonth() + 1) :
                "0" + (date_from.getMonth() + 1)) + "-";
        date += date_from.getDate() > 9 ? date_from.getDate() :
                "0" + date_from.getDate();
        return date;
    }

    public String getDate_from_string() {
        return getString(date_from);
    }

    public String getDate_to_string() {
        return getString(date_to);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UUID getRental_uid() {
        return rental_uid;
    }

    public void setRental_uid(UUID rental_uid) {
        this.rental_uid = rental_uid;
    }

    public UUID getUser_uid() {
        return user_uid;
    }

    public void setUser_uid(UUID user_uid) {
        this.user_uid = user_uid;
    }

    public UUID getLocated_scooter_uid() {
        return located_scooter_uid;
    }

    public void setLocated_scooter_uid(UUID located_scooter_uid) {
        this.located_scooter_uid = located_scooter_uid;
    }

    public UUID getPayment_uid() {
        return payment_uid;
    }

    public void setPayment_uid(UUID payment_uid) {
        this.payment_uid = payment_uid;
    }

    public UUID getTaken_from() {
        return taken_from;
    }

    public void setTaken_from(UUID taken_from) {
        this.taken_from = taken_from;
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

    public RentalStatus getStatus() {
        return status;
    }

    public void setStatus(RentalStatus status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Rental rental = (Rental) o;

        if (!Objects.equals(id, rental.id)) return false;
        if (!Objects.equals(rental_uid, rental.rental_uid)) return false;
        if (!Objects.equals(user_uid, rental.user_uid)) return false;
        if (!Objects.equals(located_scooter_uid, rental.located_scooter_uid))
            return false;
        if (!Objects.equals(payment_uid, rental.payment_uid)) return false;
        if (!Objects.equals(taken_from, rental.taken_from)) return false;
        if (!Objects.equals(return_to, rental.return_to)) return false;
        if (!Objects.equals(date_from, rental.date_from)) return false;
        if (!Objects.equals(date_to, rental.date_to)) return false;
        return status == rental.status;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (rental_uid != null ? rental_uid.hashCode() : 0);
        result = 31 * result + (user_uid != null ? user_uid.hashCode() : 0);
        result = 31 * result + (located_scooter_uid != null ? located_scooter_uid.hashCode() : 0);
        result = 31 * result + (payment_uid != null ? payment_uid.hashCode() : 0);
        result = 31 * result + (taken_from != null ? taken_from.hashCode() : 0);
        result = 31 * result + (return_to != null ? return_to.hashCode() : 0);
        result = 31 * result + (date_from != null ? date_from.hashCode() : 0);
        result = 31 * result + (date_to != null ? date_to.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Rental{" +
                "id=" + id +
                ", rental_uid=" + rental_uid +
                ", user_uid=" + user_uid +
                ", located_scooter_uid=" + located_scooter_uid +
                ", payment_uid=" + payment_uid +
                ", taken_from=" + taken_from +
                ", return_to=" + return_to +
                ", date_from=" + date_from +
                ", date_to=" + date_to +
                ", status=" + status +
                '}';
    }
}
