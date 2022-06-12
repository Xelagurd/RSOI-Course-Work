package com.example.rsoi_course_work.gateway_service.model.rental;

import com.example.rsoi_course_work.gateway_service.model.located_scooter.LocatedScooterInfo;
import com.example.rsoi_course_work.gateway_service.model.payment.PaymentInfo;
import com.example.rsoi_course_work.gateway_service.model.rental_station.RentalStationInfo;
import com.example.rsoi_course_work.gateway_service.model.user.UserInfo;

import java.util.Objects;
import java.util.UUID;

public class CreateRentalResponse {
    private UUID rental_uid;
    private UserInfo user;
    private LocatedScooterInfo located_scooter;
    private PaymentInfo payment;
    private RentalStationInfo takenFromRentalStation;
    private RentalStationInfo returnToRentalStation;
    private String date_from;
    private String date_to;
    private RentalStatus status;

    public CreateRentalResponse() {
    }

    public CreateRentalResponse(UUID rental_uid, UserInfo user, LocatedScooterInfo located_scooter, PaymentInfo payment, RentalStationInfo takenFromRentalStation, RentalStationInfo returnToRentalStation, String date_from, String date_to, RentalStatus status) {
        this.rental_uid = rental_uid;
        this.user = user;
        this.located_scooter = located_scooter;
        this.payment = payment;
        this.takenFromRentalStation = takenFromRentalStation;
        this.returnToRentalStation = returnToRentalStation;
        this.date_from = date_from;
        this.date_to = date_to;
        this.status = status;
    }

    public UUID getRental_uid() {
        return rental_uid;
    }

    public void setRental_uid(UUID rental_uid) {
        this.rental_uid = rental_uid;
    }

    public UserInfo getUser() {
        return user;
    }

    public void setUser(UserInfo user) {
        this.user = user;
    }

    public LocatedScooterInfo getLocated_scooter() {
        return located_scooter;
    }

    public void setLocated_scooter(LocatedScooterInfo located_scooter) {
        this.located_scooter = located_scooter;
    }

    public PaymentInfo getPayment() {
        return payment;
    }

    public void setPayment(PaymentInfo payment) {
        this.payment = payment;
    }

    public RentalStationInfo getTakenFromRentalStation() {
        return takenFromRentalStation;
    }

    public void setTakenFromRentalStation(RentalStationInfo takenFromRentalStation) {
        this.takenFromRentalStation = takenFromRentalStation;
    }

    public RentalStationInfo getReturnToRentalStation() {
        return returnToRentalStation;
    }

    public void setReturnToRentalStation(RentalStationInfo returnToRentalStation) {
        this.returnToRentalStation = returnToRentalStation;
    }

    public String getDate_from() {
        return date_from;
    }

    public void setDate_from(String date_from) {
        this.date_from = date_from;
    }

    public String getDate_to() {
        return date_to;
    }

    public void setDate_to(String date_to) {
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

        CreateRentalResponse that = (CreateRentalResponse) o;

        if (!Objects.equals(rental_uid, that.rental_uid)) return false;
        if (!Objects.equals(user, that.user)) return false;
        if (!Objects.equals(located_scooter, that.located_scooter))
            return false;
        if (!Objects.equals(payment, that.payment)) return false;
        if (!Objects.equals(takenFromRentalStation, that.takenFromRentalStation))
            return false;
        if (!Objects.equals(returnToRentalStation, that.returnToRentalStation))
            return false;
        if (!Objects.equals(date_from, that.date_from)) return false;
        if (!Objects.equals(date_to, that.date_to)) return false;
        return status == that.status;
    }

    @Override
    public int hashCode() {
        int result = rental_uid != null ? rental_uid.hashCode() : 0;
        result = 31 * result + (user != null ? user.hashCode() : 0);
        result = 31 * result + (located_scooter != null ? located_scooter.hashCode() : 0);
        result = 31 * result + (payment != null ? payment.hashCode() : 0);
        result = 31 * result + (takenFromRentalStation != null ? takenFromRentalStation.hashCode() : 0);
        result = 31 * result + (returnToRentalStation != null ? returnToRentalStation.hashCode() : 0);
        result = 31 * result + (date_from != null ? date_from.hashCode() : 0);
        result = 31 * result + (date_to != null ? date_to.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "RentalInfo{" +
                "rental_uid=" + rental_uid +
                ", user=" + user +
                ", located_scooter=" + located_scooter +
                ", payment=" + payment +
                ", takenFromRentalStation=" + takenFromRentalStation +
                ", returnToRentalStation=" + returnToRentalStation +
                ", date_from='" + date_from + '\'' +
                ", date_to='" + date_to + '\'' +
                ", status=" + status +
                '}';
    }
}
