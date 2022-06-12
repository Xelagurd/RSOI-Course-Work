package com.example.rsoi_course_work.gateway_service.model;

import java.util.Objects;
import java.util.UUID;

public class PaymentInfo {
    private UUID payment_uid;
    private Integer price;
    private PaymentStatus status;

    public PaymentInfo() {
    }

    public PaymentInfo(Payment payment) {
        this.payment_uid = payment.getPayment_uid();
        this.price = payment.getPrice();
        this.status = payment.getStatus();
    }

    public PaymentInfo(UUID payment_uid, Integer price, PaymentStatus status) {
        this.payment_uid = payment_uid;
        this.price = price;
        this.status = status;
    }

    public UUID getPayment_uid() {
        return payment_uid;
    }

    public void setPayment_uid(UUID payment_uid) {
        this.payment_uid = payment_uid;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public PaymentStatus getStatus() {
        return status;
    }

    public void setStatus(PaymentStatus status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PaymentInfo that = (PaymentInfo) o;

        if (!Objects.equals(payment_uid, that.payment_uid)) return false;
        if (!Objects.equals(price, that.price)) return false;
        return status == that.status;
    }

    @Override
    public int hashCode() {
        int result = payment_uid != null ? payment_uid.hashCode() : 0;
        result = 31 * result + (price != null ? price.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "PaymentInfo{" +
                "payment_uid=" + payment_uid +
                ", price=" + price +
                ", status=" + status +
                '}';
    }
}
