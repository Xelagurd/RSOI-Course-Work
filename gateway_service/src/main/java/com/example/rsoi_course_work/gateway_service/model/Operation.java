package com.example.rsoi_course_work.gateway_service.model;

import java.util.Date;
import java.util.Objects;
import java.util.UUID;

public class Operation {
    private Long id;
    private UUID operation_uid;
    private ServiceType service;
    private OperationType operation;
    private Date date;
    private UUID user_uid;
    private UUID scooter_uid;
    private UUID located_scooter_uid;
    private UUID rental_station_uid;
    private UUID rental_uid;
    private UUID payment_uid;

    public Operation() {
    }

    public Operation(Long id, UUID operation_uid, ServiceType service, OperationType operation, Date date, UUID user_uid, UUID scooter_uid, UUID located_scooter_uid, UUID rental_station_uid, UUID rental_uid, UUID payment_uid) {
        this.id = id;
        this.operation_uid = operation_uid;
        this.service = service;
        this.operation = operation;
        this.date = date;
        this.user_uid = user_uid;
        this.scooter_uid = scooter_uid;
        this.located_scooter_uid = located_scooter_uid;
        this.rental_station_uid = rental_station_uid;
        this.rental_uid = rental_uid;
        this.payment_uid = payment_uid;
    }

    public Operation(UUID operation_uid, ServiceType service, OperationType operation, Date date, UUID user_uid, UUID scooter_uid, UUID located_scooter_uid, UUID rental_station_uid, UUID rental_uid, UUID payment_uid) {
        this.operation_uid = operation_uid;
        this.service = service;
        this.operation = operation;
        this.date = date;
        this.user_uid = user_uid;
        this.scooter_uid = scooter_uid;
        this.located_scooter_uid = located_scooter_uid;
        this.rental_station_uid = rental_station_uid;
        this.rental_uid = rental_uid;
        this.payment_uid = payment_uid;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UUID getOperation_uid() {
        return operation_uid;
    }

    public void setOperation_uid(UUID operation_uid) {
        this.operation_uid = operation_uid;
    }

    public ServiceType getService() {
        return service;
    }

    public void setService(ServiceType service) {
        this.service = service;
    }

    public OperationType getOperation() {
        return operation;
    }

    public void setOperation(OperationType operation) {
        this.operation = operation;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public UUID getUser_uid() {
        return user_uid;
    }

    public void setUser_uid(UUID user_uid) {
        this.user_uid = user_uid;
    }

    public UUID getScooter_uid() {
        return scooter_uid;
    }

    public void setScooter_uid(UUID scooter_uid) {
        this.scooter_uid = scooter_uid;
    }

    public UUID getLocated_scooter_uid() {
        return located_scooter_uid;
    }

    public void setLocated_scooter_uid(UUID located_scooter_uid) {
        this.located_scooter_uid = located_scooter_uid;
    }

    public UUID getRental_station_uid() {
        return rental_station_uid;
    }

    public void setRental_station_uid(UUID rental_station_uid) {
        this.rental_station_uid = rental_station_uid;
    }

    public UUID getRental_uid() {
        return rental_uid;
    }

    public void setRental_uid(UUID rental_uid) {
        this.rental_uid = rental_uid;
    }

    public UUID getPayment_uid() {
        return payment_uid;
    }

    public void setPayment_uid(UUID payment_uid) {
        this.payment_uid = payment_uid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Operation operation1 = (Operation) o;

        if (!Objects.equals(id, operation1.id)) return false;
        if (!Objects.equals(operation_uid, operation1.operation_uid))
            return false;
        if (service != operation1.service) return false;
        if (operation != operation1.operation) return false;
        if (!Objects.equals(date, operation1.date)) return false;
        if (!Objects.equals(user_uid, operation1.user_uid)) return false;
        if (!Objects.equals(scooter_uid, operation1.scooter_uid))
            return false;
        if (!Objects.equals(located_scooter_uid, operation1.located_scooter_uid))
            return false;
        if (!Objects.equals(rental_station_uid, operation1.rental_station_uid))
            return false;
        if (!Objects.equals(rental_uid, operation1.rental_uid))
            return false;
        return Objects.equals(payment_uid, operation1.payment_uid);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (operation_uid != null ? operation_uid.hashCode() : 0);
        result = 31 * result + (service != null ? service.hashCode() : 0);
        result = 31 * result + (operation != null ? operation.hashCode() : 0);
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (user_uid != null ? user_uid.hashCode() : 0);
        result = 31 * result + (scooter_uid != null ? scooter_uid.hashCode() : 0);
        result = 31 * result + (located_scooter_uid != null ? located_scooter_uid.hashCode() : 0);
        result = 31 * result + (rental_station_uid != null ? rental_station_uid.hashCode() : 0);
        result = 31 * result + (rental_uid != null ? rental_uid.hashCode() : 0);
        result = 31 * result + (payment_uid != null ? payment_uid.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Operation{" +
                "id=" + id +
                ", operation_uid=" + operation_uid +
                ", service=" + service +
                ", operation=" + operation +
                ", date=" + date +
                ", user_uid=" + user_uid +
                ", scooter_uid=" + scooter_uid +
                ", located_scooter_uid=" + located_scooter_uid +
                ", rental_station_uid=" + rental_station_uid +
                ", rental_uid=" + rental_uid +
                ", payment_uid=" + payment_uid +
                '}';
    }
}
