package com.example.rsoi_course_work.front.model.statistic_operation;

import java.util.Date;
import java.util.Objects;
import java.util.UUID;

public class StatisticOperation {
    private Long id;
    private UUID statistic_operation_uid;
    private ServiceType service_type;
    private StatisticOperationType statistic_operation_type;
    private Date date;
    private UUID user_uid;
    private UUID scooter_uid;
    private UUID located_scooter_uid;
    private UUID rental_station_uid;
    private UUID rental_uid;
    private UUID payment_uid;

    public StatisticOperation() {
    }

    public StatisticOperation(Long id, UUID statistic_operation_uid, ServiceType service_type, StatisticOperationType statistic_operation_type, Date date, UUID user_uid, UUID scooter_uid, UUID located_scooter_uid, UUID rental_station_uid, UUID rental_uid, UUID payment_uid) {
        this.id = id;
        this.statistic_operation_uid = statistic_operation_uid;
        this.service_type = service_type;
        this.statistic_operation_type = statistic_operation_type;
        this.date = date;
        this.user_uid = user_uid;
        this.scooter_uid = scooter_uid;
        this.located_scooter_uid = located_scooter_uid;
        this.rental_station_uid = rental_station_uid;
        this.rental_uid = rental_uid;
        this.payment_uid = payment_uid;
    }

    public StatisticOperation(UUID statistic_operation_uid, ServiceType service_type, StatisticOperationType statistic_operation_type, Date date, UUID user_uid, UUID scooter_uid, UUID located_scooter_uid, UUID rental_station_uid, UUID rental_uid, UUID payment_uid) {
        this.statistic_operation_uid = statistic_operation_uid;
        this.service_type = service_type;
        this.statistic_operation_type = statistic_operation_type;
        this.date = date;
        this.user_uid = user_uid;
        this.scooter_uid = scooter_uid;
        this.located_scooter_uid = located_scooter_uid;
        this.rental_station_uid = rental_station_uid;
        this.rental_uid = rental_uid;
        this.payment_uid = payment_uid;
    }

    public StatisticOperationInfo getInfo() {
        return new StatisticOperationInfo(statistic_operation_uid, service_type, statistic_operation_type,
                date, user_uid, scooter_uid, located_scooter_uid, rental_station_uid, rental_uid, payment_uid);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UUID getStatistic_operation_uid() {
        return statistic_operation_uid;
    }

    public void setStatistic_operation_uid(UUID statistic_operation_uid) {
        this.statistic_operation_uid = statistic_operation_uid;
    }

    public ServiceType getService_type() {
        return service_type;
    }

    public void setService_type(ServiceType service_type) {
        this.service_type = service_type;
    }

    public StatisticOperationType getStatistic_operation_type() {
        return statistic_operation_type;
    }

    public void setStatistic_operation_type(StatisticOperationType statistic_operation_type) {
        this.statistic_operation_type = statistic_operation_type;
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

        StatisticOperation that = (StatisticOperation) o;

        if (!Objects.equals(id, that.id)) return false;
        if (!Objects.equals(statistic_operation_uid, that.statistic_operation_uid))
            return false;
        if (service_type != that.service_type) return false;
        if (statistic_operation_type != that.statistic_operation_type) return false;
        if (!Objects.equals(date, that.date)) return false;
        if (!Objects.equals(user_uid, that.user_uid)) return false;
        if (!Objects.equals(scooter_uid, that.scooter_uid)) return false;
        if (!Objects.equals(located_scooter_uid, that.located_scooter_uid))
            return false;
        if (!Objects.equals(rental_station_uid, that.rental_station_uid))
            return false;
        if (!Objects.equals(rental_uid, that.rental_uid)) return false;
        return Objects.equals(payment_uid, that.payment_uid);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (statistic_operation_uid != null ? statistic_operation_uid.hashCode() : 0);
        result = 31 * result + (service_type != null ? service_type.hashCode() : 0);
        result = 31 * result + (statistic_operation_type != null ? statistic_operation_type.hashCode() : 0);
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
        return "StatisticOperation{" +
                "id=" + id +
                ", statistic_operation_uid=" + statistic_operation_uid +
                ", service_type=" + service_type +
                ", statistic_operation_type=" + statistic_operation_type +
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
