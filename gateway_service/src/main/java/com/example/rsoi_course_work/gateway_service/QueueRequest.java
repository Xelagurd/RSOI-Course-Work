package com.example.rsoi_course_work.gateway_service;

import java.util.UUID;

public class QueueRequest {
    private QueueRequestType queueRequestType = null;
    private UUID userUid = null;
    private UUID rentalUid = null;
    private UUID paymentUid = null;
    private UUID locatedScooterUid = null;
    private Boolean availability = null;

    public QueueRequestType getQueueRequestType() {
        return queueRequestType;
    }

    public UUID getUserUid() {
        return userUid;
    }

    public UUID getRentalUid() {
        return rentalUid;
    }

    public UUID getPaymentUid() {
        return paymentUid;
    }

    public UUID getLocatedScooterUid() {
        return locatedScooterUid;
    }

    public boolean isAvailability() {
        return availability;
    }

    public void setCancelUserRental(UUID userUid, UUID rentalUid) {
        this.queueRequestType = QueueRequestType.CANCEL_USER_RENTAL;
        this.userUid = userUid;
        this.rentalUid = rentalUid;
    }

    public void setFinishUserRental(UUID userUid, UUID rentalUid) {
        this.queueRequestType = QueueRequestType.FINISH_USER_RENTAL;
        this.userUid = userUid;
        this.rentalUid = rentalUid;
    }

    public void setUpdateLocatedScooterReserve(UUID locatedScooterUid, Boolean availability) {
        this.queueRequestType = QueueRequestType.UPDATE_SCOOTER_RESERVE;
        this.locatedScooterUid = locatedScooterUid;
        this.availability = availability;
    }

    public void setCancelPayment(UUID paymentUid) {
        this.queueRequestType = QueueRequestType.CANCEL_PAYMENT;
        this.paymentUid = paymentUid;
    }
}
