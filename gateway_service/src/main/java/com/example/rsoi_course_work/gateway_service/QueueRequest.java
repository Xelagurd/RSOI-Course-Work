package com.example.rsoi_course_work.gateway_service;

import java.util.UUID;

public class QueueRequest {
    private QueueRequestType queueRequestType = null;
    private String username = null;
    private UUID rentalUid = null;
    private UUID paymentUid = null;
    private UUID scooterUid = null;
    private Boolean availability = null;

    public QueueRequestType getQueueRequestType() {
        return queueRequestType;
    }

    public String getUsername() {
        return username;
    }

    public UUID getRentalUid() {
        return rentalUid;
    }

    public UUID getPaymentUid() {
        return paymentUid;
    }

    public UUID getScooterUid() {
        return scooterUid;
    }

    public boolean isAvailability() {
        return availability;
    }

    public void setCancelUserRental(String username, UUID rentalUid) {
        this.queueRequestType = QueueRequestType.CANCEL_USER_RENTAL;
        this.username = username;
        this.rentalUid = rentalUid;
    }

    public void setFinishUserRental(String username, UUID rentalUid) {
        this.queueRequestType = QueueRequestType.FINISH_USER_RENTAL;
        this.username = username;
        this.rentalUid = rentalUid;
    }

    public void setUpdateScooterReserve(UUID scooterUid, Boolean availability) {
        this.queueRequestType = QueueRequestType.UPDATE_SCOOTER_RESERVE;
        this.scooterUid = scooterUid;
        this.availability = availability;
    }

    public void setCancelPayment(UUID paymentUid) {
        this.queueRequestType = QueueRequestType.CANCEL_PAYMENT;
        this.paymentUid = paymentUid;
    }
}
