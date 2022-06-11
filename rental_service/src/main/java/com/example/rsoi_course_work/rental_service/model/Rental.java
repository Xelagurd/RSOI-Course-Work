package com.example.rsoi_course_work.rental_service.model;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "rentals", schema = "rentals")
public class Rental {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "serial")
    private Long id;

    @Column(nullable = false, unique = true)
    private UUID rental_uid;

    @Column(nullable = false, length = 80)
    private String username;

    @Column(nullable = false)
    private UUID payment_uid;

    @Column(nullable = false)
    private UUID scooter_uid;

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date date_from;

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date date_to;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private RentalStatus status;

    public Rental() {
    }

    public Rental(Long id, UUID rental_uid, String username, UUID payment_uid, UUID scooter_uid, Date date_from, Date date_to, RentalStatus status) {
        super();
        this.id = id;
        this.rental_uid = rental_uid;
        this.username = username;
        this.payment_uid = payment_uid;
        this.scooter_uid = scooter_uid;
        this.date_from = date_from;
        this.date_to = date_to;
        this.status = status;
    }

    public Rental(UUID rental_uid, String username, UUID payment_uid, UUID scooter_uid, Date date_from, Date date_to, RentalStatus status) {
        super();
        this.rental_uid = rental_uid;
        this.username = username;
        this.payment_uid = payment_uid;
        this.scooter_uid = scooter_uid;
        this.date_from = date_from;
        this.date_to = date_to;
        this.status = status;
    }

    public static String getString(Date date_from) {
        String date = (date_from.getYear() + 1900) + "-";
        date += ((date_from.getMonth() + 1) > 9 ? (date_from.getMonth() + 1) :
                "0" + (date_from.getMonth() + 1)) + "-";
        date += date_from.getDate() > 9 ? date_from.getDate() :
                "0" + date_from.getDate();
        return date;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public UUID getPayment_uid() {
        return payment_uid;
    }

    public void setPayment_uid(UUID payment_uid) {
        this.payment_uid = payment_uid;
    }

    public UUID getScooter_uid() {
        return scooter_uid;
    }

    public void setScooter_uid(UUID scooter_uid) {
        this.scooter_uid = scooter_uid;
    }

    public Date getDate_from() {
        return date_from;
    }

    public void setDate_from(Date date_from) {
        this.date_from = date_from;
    }

    public String getDate_from_string() {
        return getString(date_from);
    }

    public Date getDate_to() {
        return date_to;
    }

    public void setDate_to(Date date_to) {
        this.date_to = date_to;
    }

    public String getDate_to_string() {
        return getString(date_to);
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
        if (!(o instanceof Rental)) return false;

        Rental rental = (Rental) o;

        if (!Objects.equals(id, rental.id)) return false;
        if (!Objects.equals(rental_uid, rental.rental_uid)) return false;
        if (!Objects.equals(username, rental.username)) return false;
        if (!Objects.equals(payment_uid, rental.payment_uid)) return false;
        if (!Objects.equals(scooter_uid, rental.scooter_uid)) return false;
        if (!Objects.equals(date_from, rental.date_from)) return false;
        if (!Objects.equals(date_to, rental.date_to)) return false;
        return status == rental.status;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (rental_uid != null ? rental_uid.hashCode() : 0);
        result = 31 * result + (username != null ? username.hashCode() : 0);
        result = 31 * result + (payment_uid != null ? payment_uid.hashCode() : 0);
        result = 31 * result + (scooter_uid != null ? scooter_uid.hashCode() : 0);
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
                ", username='" + username + '\'' +
                ", payment_uid=" + payment_uid +
                ", scooter_uid=" + scooter_uid +
                ", date_from=" + date_from +
                ", date_to=" + date_to +
                ", status=" + status +
                '}';
    }
}
